package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.exceptions.InvalidUserInput;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.ModelMapper;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;
import com.example.automobilerestapiapp.repositories.ModelRepository;
import com.example.automobilerestapiapp.repositories.ProducerRepository;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService{

  private final ModelRepository modelRepository;

  private final ProducerRepository producerRepository;

  private final ProducerService producerService;

  @Autowired
  public ModelServiceImpl(ModelRepository modelRepository, ProducerRepository producerRepository, ProducerService producerService) {
    this.modelRepository = modelRepository;
    this.producerRepository = producerRepository;
    this.producerService = producerService;
  }

  @Override
  public List<ModelResponse> getAllModels() {
    List<ModelResponse> modelsResponse = new ArrayList<>();
    modelRepository.findAll().forEach(m -> modelsResponse.add(ModelMapper.toModelResponse(m)));
    return modelsResponse;
  }

  @Override
  public ModelResponse getById(Long id) {
    Model model = modelRepository.getModelById(id).orElseThrow(() -> new RecordNotFoundException(id));
    return ModelMapper.toModelResponse(model);
  }

  @Override
  public ModelResponse insert(StoreModelRequest newModel) {
    validateReleaseYear(newModel.getReleaseYear());
    Producer producer = producerService.getProducerEntity(newModel.getProducerId());
    Model model = ModelMapper.fromStoreModelRequest(newModel,producer);
    modelRepository.save(model);
    producer.addNewModel(model);
    producerRepository.save(producer);

    return ModelMapper.toModelResponse(model);
  }

  @Override
  public ModelResponse updateOrSaveNew(StoreModelRequest newModel, Long id) {
    return modelRepository.getModelById(id)
        .map(model -> {
          validateReleaseYear(newModel.getReleaseYear());

          Long newProducerId = newModel.getProducerId();
          Producer currentProducer;
          if (!newProducerId.equals(model.getProducer().getId())) {
            currentProducer = producerService.getProducerEntity(newProducerId);
            Producer oldProducer = model.getProducer();
            oldProducer.removeModel(model);
            currentProducer.addNewModel(model);
          } else {
            currentProducer = model.getProducer();
          }
          model.setNewProperties(newModel, currentProducer);
          modelRepository.save(model);
          producerRepository.save(currentProducer);

          return ModelMapper.toModelResponse(model);
        })
        .orElseGet(() -> insert(newModel));
  }
  @Override
  public ModelResponse deleteById(Long id) {
    Model model = getModelEntity(id);
    modelRepository.delete(model);
    return ModelMapper.toModelResponse(model);
  }

  @Override
  public Model getModelEntity(Long id) {
   return modelRepository.getModelById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  @Override
  public void validateReleaseYear(Integer year) {
    if (year > Year.now().getValue()) {
      throw new InvalidUserInput("Release year can not be higher than current year");
    }
    if (year < 1886) {
      throw new InvalidUserInput("Release year can not be lower than '1886'");
    }
  }
}