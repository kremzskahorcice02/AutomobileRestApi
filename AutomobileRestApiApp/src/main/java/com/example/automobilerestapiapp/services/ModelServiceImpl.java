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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModelServiceImpl implements ModelService{

  private final ModelRepository modelRepository;

  private final ProducerRepository producerRepository;

  @Autowired
  public ModelServiceImpl(ModelRepository modelRepository, ProducerRepository producerRepository) {
    this.modelRepository = modelRepository;
    this.producerRepository = producerRepository;
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
    if (newModel.getReleaseYear() > Year.now().getValue()) {
      throw new InvalidUserInput("Release year can not be higher than current year");
    }

    Optional<Producer> producerOpt = producerRepository.getProducerById(newModel.getProducerId());
    if (producerOpt.isEmpty()) {
      throw new RecordNotFoundException(newModel.getProducerId());
    }

    Producer producer = producerOpt.get();
    Model model = ModelMapper.fromStoreModelRequest(newModel,producer);
    producerRepository.save(producer);
    modelRepository.save(model);
    return ModelMapper.toModelResponse(model);
  }

  @Override
  public ModelResponse updateOrSaveNew(StoreModelRequest newModel, Long id) {
    Long producerId = newModel.getProducerId();
    Producer producer = producerRepository.getProducerById(producerId).orElseThrow(()-> new RecordNotFoundException(id));

    return modelRepository.getModelById(id)
        .map(model -> {
          model.setNewProperties(newModel, producer);
          producer.addNewModel(model);
          modelRepository.save(model);
          producerRepository.save(producer);
          return ModelMapper.toModelResponse(model);
        })
        .orElseGet(() -> insert(newModel));
  }
  @Override
  public ModelResponse deleteById(Long id) {
    if (!modelRepository.existsById(id)) {
      throw new RecordNotFoundException(id);
    }
    return ModelMapper.toModelResponse(modelRepository.deleteModelById(id));
  }
}