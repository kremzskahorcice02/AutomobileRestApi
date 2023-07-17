package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileDriveAbilityResponse;
import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import java.time.LocalDate;
import java.util.List;

public interface AutomobileService {

  /**
   * Fetches all Automobile objects from repository and converts the objects to dtos.
   * @return arraylist of automobile dtos
   */
  List<AutomobileResponse> getAll();

  /**
   * Fetches an Automobile object from repo of given id and converts the object to dto.
   * @param id the id of the desired Automobile
   * @throws RecordNotFoundException if Automobile of given id was not found
   * @return dto of the Automobile with given id
   */
  AutomobileResponse getById(Long id);

  /**
   * Creates new automobile object from dto and saves it in a database. Before the saving it calls methods
   * to validate 'date of creation' of the automobile is in specific range and format and to validate
   * if id of the automobile's model exists. In case model exists and its production is set to active,
   * steps to change the relationships in model-automobile relationship are made.
   * <p>
   * For validation of the 'date of creation' see the
   * {@link #validateDateOfCreation(String)}  method.
   * For validation of the model's id existence see the
   * {@link com.example.automobilerestapiapp.services.ModelService#getModelEntity(Long)} method.
   * For the check if model's productions is still active see the
   * {@link #validateModelIsActive(Model)} method.
   * @param newAutomobile dto object with data to create new automobile entity
   * @return dto of the automobile that was created
   */
  AutomobileResponse insert(StoreAutomobileRequest newAutomobile);

  /**
   * Saves a list of Automobiles to the database
   * @param automobiles list of Automobile objects
   */
  void insertAll(List<Automobile> automobiles);

  /**
   * Method fetches automobile of given id and updates
   * all the fields respectively to the new data object passed as an argument. Before storing the object
   * back to database it provides a validation for field 'dateOfCreation'
   * (see more {@link #validateDateOfCreation(String)} ). If new data for field 'modelId' is provided,
   * a check for existence of the model object is provided
   * (see more {@link com.example.automobilerestapiapp.services.ModelService#getModelEntity(Long)} ).
   * In case model exists and its production is set to active {@link #validateModelIsActive(Model)},
   * steps to change the relationships in model-automobile relationship are made.
   * <p>
   * If automobile of given id was not found it calls the {@link #insert(StoreAutomobileRequest)} method to create
   * completely new automobile object and to store it in a database.
   * @param updatedAutomobile dto object with data to update the automobile object
   * @param id id of the automobile object to be updated
   * @return dto of the automobile that was update or newly created
   */
  AutomobileResponse updateOrInsertNew(StoreAutomobileRequest updatedAutomobile, Long id);

  /**
   * Finds an automobile object that has specified id and calls the repository to delete it from the database.
   * @param id the id of the automobile to be deleted
   * @return dto of the automobile that was deleted
   */
  AutomobileResponse deleteById(Long id);

  /**
   * Searches for an automobile with specific id.
   * @param id the id of the requested automobile object
   * @throws RecordNotFoundException if no automobile object of given id was found
   * @return the automobile object
   */
  Automobile getAutomobileEntity(Long id);

  /**
   * Method calls fetches data from a database about the sum of driveable automobiles and nonDriveable
   * automobiles and returns them in dto object
   * @return dto with counts of driveable and nonDriveable automobiles
   */
  AutomobileDriveAbilityResponse countByDriveAbility();

  /**
   * Method validates if the String passed as an argument is in predefined format (yyyy-MM-dd) via parsing.
   * If the String is successfully parsed to LocalDate object .
   * If no error occurs during parsing, the method checks if the date falls into  predefined range
   * (1886-today both inclusive)
   * @param date String representing to be validated
   * @throws InvalidDateException if condition of format and range are not met
   * @return localDate object
   */
  LocalDate validateDateOfCreation(String date);

  /**
   * Validated the model passed as an argument is active
   * and therefore new children objects of type Automobile can be instantiated
   */
  void validateModelIsActive(Model model);
}