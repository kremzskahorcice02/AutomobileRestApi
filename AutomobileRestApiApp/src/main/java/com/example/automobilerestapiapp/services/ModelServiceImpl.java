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

  /**
   * Fetches all model objects from repo and converts the objects to dtos.
   * @return arraylist of model dtos
   */
  @Override
  public List<ModelResponse> getAllModels() {
    List<ModelResponse> modelsResponse = new ArrayList<>();
    modelRepository.findAll().forEach(m -> modelsResponse.add(ModelMapper.toModelResponse(m)));
    return modelsResponse;
  }

  /**
   * Fetches a model object from repo of given id and converts the object to dto.
   * @param id the id of the desired model
   * @throws RecordNotFoundException if model of given id was not found
   * @return dto of the model with given id
   */
  @Override
  public ModelResponse getById(Long id) {
    Model model = modelRepository.getModelById(id).orElseThrow(() -> new RecordNotFoundException(id));
    return ModelMapper.toModelResponse(model);
  }

  /**
   * Creates new model object from dto and saves it in a database. Before the saving it calls methods
   * to validate release year of the model is in specific range and if id of the model's producer exists.
   * <p>
   * For validation of the release year see the
   * {@link #validateReleaseYear(Integer)} method
   * for validation of the producer's id existence.
   * {@link com.example.automobilerestapiapp.repositories.ProducerRepository#getProducerById(Long)} method
   * @param updatedModel dto object with data to create new model entity
   * @return dto of the model that was created
   */
  @Override
  public ModelResponse insert(StoreModelRequest updatedModel) {
    validateReleaseYear(updatedModel.getReleaseYear());
    Producer producer = producerService.getProducerEntity(updatedModel.getProducerId());
    Model model = ModelMapper.fromStoreModelRequest(updatedModel,producer);
    modelRepository.save(model);
    producer.addNewModel(model);
    producerRepository.save(producer);

    return ModelMapper.toModelResponse(model);
  }

  /**
   * Serves for updating model object stored in database. It fetches model of given id and updates
   * all the fields respectively to the new data object passed as an argument. Before storing the object
   * back to database it provides a validation for field 'releaseYear'
   * (see more {@link #validateReleaseYear(Integer)}). If new data for field 'producerId' is provided,
   * a check for existence of the producer object is provided
   * (see more {@link com.example.automobilerestapiapp.services.ProducerService#getProducerEntity(Long)).
   * In case of producer existence, steps to change the relationships in producer-model relationship are made.
   * <p>
   * If model of given id was not found it calls the {@link #insert(StoreModelRequest)} method to create
   * completely new model object and to store it in a database.
   * @param updatedModel updatedModel dto object with data to update the model object
   * @param id id of the model object to be updated
   * @return dto of the model that was update or newly created
   */
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

          return ModelMapper.toModelResponse(model);
        })
        .orElseGet(() -> insert(updatedModel));
  }

  /**
   * Finds a model object that has specified id and calls the repository to delete it from the database.
   * @param id the id of the model to be deleted
   * @return dto of the model that was deleted
   */
  @Override
  public ModelResponse deleteById(Long id) {
    Model model = getModelEntity(id);
    modelRepository.delete(model);
    return ModelMapper.toModelResponse(model);
  }

  /**
   * Searches for a model with specific id.
   * @param id the id of the requested model object
   * @throws RecordNotFoundException if no model object of given id was found
   * @return the model object
   */
  @Override
  public Model getModelEntity(Long id) {
   return modelRepository.getModelById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  /**
   * Validates that given year is in specific range. Accepted range is between 1886 and current year (both inclusive).
   * @param year integer representation of Year to be validated
   * @throws InvalidUserInput if release year represent future or if represents year before 1886
   */
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