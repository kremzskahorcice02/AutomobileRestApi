package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.exceptions.InvalidUserInput;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.ModelMapper;
import com.example.automobilerestapiapp.models.Log;
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
  private final LogService logService;

  @Autowired
  public ModelServiceImpl(ModelRepository modelRepository,
      ProducerRepository producerRepository,
      ProducerService producerService, LogService logService) {
    this.modelRepository = modelRepository;
    this.producerRepository = producerRepository;
    this.producerService = producerService;
    this.logService = logService;
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
  public ModelResponse insert(StoreModelRequest updatedModel) {
    validateReleaseYear(updatedModel.getReleaseYear());
    Producer producer = producerService.getProducerEntity(updatedModel.getProducerId());
    Model model = ModelMapper.fromStoreModelRequest(updatedModel,producer);
    modelRepository.save(model);
    producer.addNewModel(model);
    producerRepository.save(producer);
    logService.log(new Log().success().setMessage("New Model created"));

    return ModelMapper.toModelResponse(model);
  }

  @Override
  public ModelResponse updateOrSaveNew(StoreModelRequest updatedModel, Long id) {
    return modelRepository.getModelById(id)
        .map(model -> {
          validateReleaseYear(updatedModel.getReleaseYear());

          Long newProducerId = updatedModel.getProducerId();
          Producer currentProducer;
          if (!newProducerId.equals(model.getProducer().getId())) {
            currentProducer = producerService.getProducerEntity(newProducerId);
            Producer oldProducer = model.getProducer();
            oldProducer.removeModel(model);
            currentProducer.addNewModel(model);
          } else {
            currentProducer = model.getProducer();
          }
          model.setNewProperties(updatedModel, currentProducer);
          modelRepository.save(model);
          producerRepository.save(currentProducer);
          logService.log(new Log().success().setMessage("Model updated"));

          return ModelMapper.toModelResponse(model);
        })
        .orElseGet(() -> insert(updatedModel));
  }

  @Override
  public ModelResponse deleteById(Long id) {
    Model model = getModelEntity(id);
    modelRepository.delete(model);
    logService.log(new Log().success().setMessage("Model deleted"));
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