package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
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


  @Override
  public AutomobileResponse getById(Long id) {
    Automobile auto =  automobileRepository.findById(id).orElseThrow(()->new RecordNotFoundException(id));
    return AutomobileMapper.toAutomobileResponse(auto);
  }

  /**
   * Creates new automobile object from dto and saves it in a database. Before the saving it calls methods
   * to validate 'date of creation' of the automobile is in specific range and format and to validate
   * if id of the automobile's model exists.
   * <p>
   * For validation of the 'date of creation' see the
   * {@link #validateDateOfCreation(String)}  method
   * for validation of the producer's id existence.
   * {@link com.example.automobilerestapiapp.services.ModelService#getModelEntity(Long)} method
   * @param updatedAutomobile dto object with data to create new automobile entity
   * @return dto of the automobile that was created
   */

  @Override
  public AutomobileResponse insert(StoreAutomobileRequest updatedAutomobile) {
      LocalDate dateOfCreation = validateDateOfCreation(updatedAutomobile.getDateOfCreation());
      Model model = modelService.getModelEntity(updatedAutomobile.getModelId());
      Automobile auto = AutomobileMapper.fromStoreAutomobileRequest(updatedAutomobile,dateOfCreation,model);
      model.addNewAutomobile(auto);
      modelRepository.save(model);
      automobileRepository.save(auto);
      return AutomobileMapper.toAutomobileResponse(auto);
  }

  /**
   * Serves for updating automobile object stored in database. It fetches automobile of given id and updates
   * all the fields respectively to the new data object passed as an argument. Before storing the object
   * back to database it provides a validation for field 'dateOfCreation'
   * (see more {@link #validateDateOfCreation(String)} ). If new data for field 'modelId' is provided,
   * a check for existence of the model object is provided
   * (see more {@link com.example.automobilerestapiapp.services.ModelService#getModelEntity(Long)} ).
   * In case of model existence, steps to change the relationships in model-automobile relationship are made.
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
   * Searches for a automobile with specific id.
   * @param id the id of the requested automobile object
   * @throws RecordNotFoundException if no automobile object of given id was found
   * @return the automobile object
   */
  @Override
  public Automobile getAutomobileEntity(Long id) {
    return automobileRepository.getAutomobileById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  @Override
  public LocalDate validateDateOfCreation(String date) {
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
}