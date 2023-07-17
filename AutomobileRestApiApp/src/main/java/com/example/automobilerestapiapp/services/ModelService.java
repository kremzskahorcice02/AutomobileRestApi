package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.exceptions.InvalidUserInput;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.models.Model;
import java.util.List;

public interface ModelService {

  /**
   * Fetches all model objects from repo and converts the objects to dtos.
   * @return arraylist of model dtos
   */
  List<ModelResponse> getAllModels();

  /**
   * Fetches a model object from repo of given id and converts the object to dto.
   * @param id the id of the desired model
   * @throws RecordNotFoundException if model of given id was not found
   * @return dto of the model with given id
   */
  ModelResponse getById(Long id);

  /**
   * Creates new model object from dto and saves it in a database. Before the saving it calls methods
   * to validate release year of the model is in specific range and if id of the model's producer exists.
   * <p>
   * For validation of the release year see the
   * {@link #validateReleaseYear(Integer)} method
   * for validation of the producer's id existence.
   * {@link com.example.automobilerestapiapp.repositories.ProducerRepository#getProducerById(Long)} method
   * @param newModel dto object with data to create new model entity
   * @return dto of the model that was created
   */
  ModelResponse insert(StoreModelRequest newModel);

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
  ModelResponse updateOrSaveNew(StoreModelRequest model, Long id);

  /**
   * Finds a model object that has specified id and calls the repository to delete it from the database.
   * @param id the id of the model to be deleted
   * @return dto of the model that was deleted
   */
  ModelResponse deleteById(Long id);

  /**
   * Searches for a model with specific id.
   * @param id the id of the requested model object
   * @throws RecordNotFoundException if no model object of given id was found
   * @return the model object
   */
  Model getModelEntity(Long id);

  /**
   * Validates that given year is in specific range. Accepted range is between 1886 and current year (both inclusive).
   * @param year integer representation of Year to be validated
   * @throws InvalidUserInput if release year represent future or if represents year before 1886
   */
  void validateReleaseYear(Integer year);
}