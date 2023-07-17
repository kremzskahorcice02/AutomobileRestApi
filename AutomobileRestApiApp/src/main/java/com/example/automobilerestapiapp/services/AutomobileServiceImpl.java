package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileDriveAbilityResponse;
import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
import com.example.automobilerestapiapp.exceptions.ModelNoLongerActiveException;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.AutomobileMapper;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.repositories.AutomobileRepository;
import com.example.automobilerestapiapp.repositories.ModelRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutomobileServiceImpl implements AutomobileService{

  private final AutomobileRepository automobileRepository;

  private final ModelRepository modelRepository;

  private final ModelService modelService;
  @Autowired
  public AutomobileServiceImpl(AutomobileRepository automobileRepository,
      ModelRepository modelRepository,
      ModelService modelService) {
    this.automobileRepository = automobileRepository;
    this.modelRepository = modelRepository;
    this.modelService = modelService;
  }

  /**
   * Fetches all automobile objects from repo and converts the objects to dtos.
   * @return arraylist of automobile dtos
   */
  @Override
  public List<AutomobileResponse> getAll() {
    List<AutomobileResponse> automobileList = new ArrayList<>();
    automobileRepository.findAll().forEach(auto -> automobileList.add(AutomobileMapper.toAutomobileResponse(auto)));
    return automobileList;
  }

  /**
   * Fetches an automobile object of given id form the repository and converts the object to dto.
   * @param id the id of the desired automobile
   * @throws RecordNotFoundException if automobile of given id was not found
   * @return dto of the automobile with given id
   */
  @Override
  public AutomobileResponse getById(Long id) {
    Automobile auto =  automobileRepository.findById(id).orElseThrow(()->new RecordNotFoundException(id));
    return AutomobileMapper.toAutomobileResponse(auto);
  }

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
   * @param updatedAutomobile dto object with data to create new automobile entity
   * @return dto of the automobile that was created
   */

  @Override
  public AutomobileResponse insert(StoreAutomobileRequest updatedAutomobile) {
      LocalDate dateOfCreation = validateDateOfCreation(updatedAutomobile.getDateOfCreation());
      Model model = modelService.getModelEntity(updatedAutomobile.getModelId());
      validateModelIsActive(model);
      Automobile auto = AutomobileMapper.fromStoreAutomobileRequest(updatedAutomobile,dateOfCreation,model);
      model.addNewAutomobile(auto);
      modelRepository.save(model);
      automobileRepository.save(auto);
      return AutomobileMapper.toAutomobileResponse(auto);
  }

  /**
   * Saves a list of Automobiles to the database
   * @param automobiles list of Automobile objects
   */
  @Override
  public void insertAll(List<Automobile> automobiles) {
    automobileRepository.saveAll(automobiles);
  }

  /**
   * Serves for updating automobile object stored in database. It fetches automobile of given id and updates
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
  @Override
  public AutomobileResponse updateOrInsertNew(StoreAutomobileRequest updatedAutomobile, Long id) {
    return automobileRepository.getAutomobileById(id)
        .map(auto -> {

          Long newModelId = updatedAutomobile.getModelId();
          Model model;
          if (!newModelId.equals(auto.getModel().getId())) {
            model = modelService.getModelEntity(newModelId);
            validateModelIsActive(model);
            Model oldModel = auto.getModel();
            oldModel.removeAutomobile(auto);
            model.addNewAutomobile(auto);
          } else {
            model = auto.getModel();
          }
          LocalDate newDateOfCreation = validateDateOfCreation(updatedAutomobile.getDateOfCreation());
          auto.setNewProperties(updatedAutomobile, model, newDateOfCreation);
          automobileRepository.save(auto);
          modelRepository.save(model);

          return AutomobileMapper.toAutomobileResponse(auto);
        })
        .orElseGet(() -> insert(updatedAutomobile));
  }

  /**
   * Finds an automobile object that has specified id and calls the repository to delete it from the database.
   * @param id the id of the automobile to be deleted
   * @return dto of the automobile that was deleted
   */
  @Override
  public AutomobileResponse deleteById(Long id) {
    Automobile automobile = getAutomobileEntity(id);
    automobileRepository.delete(automobile);
    return AutomobileMapper.toAutomobileResponse(automobile);
  }

  /**
   * Searches for an automobile with specific id.
   * @param id the id of the requested automobile object
   * @throws RecordNotFoundException if no automobile object of given id was found
   * @return the automobile object
   */
  @Override
  public Automobile getAutomobileEntity(Long id) {
    return automobileRepository.getAutomobileById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  /**
   * Method calls fetches data from a database about the sum of driveable automobiles and nonDriveable
   * automobiles and returns them in dto object
   * @return dto with counts of driveable and nonDriveable automobiles
   */
  @Override
  public AutomobileDriveAbilityResponse countByDriveAbility() {
    Integer driveable = automobileRepository.countAutomobilesByIsDriveableTrue();
    Integer notDriveable = automobileRepository.countAutomobilesByIsDriveableFalse();
    return new AutomobileDriveAbilityResponse(driveable,notDriveable);
  }


  /**
   * Method validates if the String passed as an argument is in predefined format (yyyy-MM-dd) via parsing.
   * If the String is successfully parsed to LocalDate object .
   * If no error occurs during parsing, the method checks if the date falls into  predefined range
   * (1886-today both inclusive)
   * @param date String representing to be validated
   * @throws InvalidDateException if condition of format and range are not met
   * @return localDate object
   */
  @Override
  public LocalDate validateDateOfCreation(String date){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate parsedDate;
    try {
      parsedDate = LocalDate.parse(date, formatter);
    } catch (RuntimeException e) {
      throw new InvalidDateException("Field 'dateOfCreation' has to be valid date in format yyyy-MM-dd");
    }
    if (parsedDate.isAfter(LocalDate.now()) || parsedDate.isBefore(LocalDate.of(1886,1,29))) {
      throw new InvalidDateException("Field 'dateOfCreation' can not represent future or be before 1886-01-29");
    }
    return parsedDate;
  }

  /**
   * Validated the model passed as an argument is active
   * and therefore new children objects of type Automobile can be instantiated
   */
  @Override
  public void validateModelIsActive(Model model) {
    if (!model.getIsActive()) {
      throw new ModelNoLongerActiveException("Model is not active anymore. New Automobile can not be created");
    }
  }
}