package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileDriveAbilityResponse;
import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
import com.example.automobilerestapiapp.exceptions.ModelNoLongerActiveException;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.AutomobileMapper;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Log;
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

  private final LogService logService;
  @Autowired
  public AutomobileServiceImpl(AutomobileRepository automobileRepository,
      ModelRepository modelRepository,ModelService modelService, LogService logService) {
    this.automobileRepository = automobileRepository;
    this.modelRepository = modelRepository;
    this.modelService = modelService;
    this.logService = logService;
  }
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



  @Override
  public AutomobileResponse insert(StoreAutomobileRequest updatedAutomobile) {
      LocalDate dateOfCreation = validateDateOfCreation(updatedAutomobile.getDateOfCreation());
      Model model = modelService.getModelEntity(updatedAutomobile.getModelId());
      validateModelIsActive(model);
      Automobile auto = AutomobileMapper.fromStoreAutomobileRequest(updatedAutomobile,dateOfCreation,model);
      model.addNewAutomobile(auto);
      modelRepository.save(model);
      automobileRepository.save(auto);
      logService.log(new Log().success().setMessage("New Automobile created"));
      return AutomobileMapper.toAutomobileResponse(auto);
  }
  @Override
  public void insertAll(List<Automobile> automobiles) {
    automobileRepository.saveAll(automobiles);
  }
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
          logService.log(new Log().success().setMessage("Automobile updated"));

          return AutomobileMapper.toAutomobileResponse(auto);
        })
        .orElseGet(() -> insert(updatedAutomobile));
  }

  @Override
  public AutomobileResponse deleteById(Long id) {
    Automobile automobile = getAutomobileEntity(id);
    automobileRepository.delete(automobile);
    logService.log(new Log().success().setMessage("Automobile deleted"));
    return AutomobileMapper.toAutomobileResponse(automobile);
  }

  @Override
  public Automobile getAutomobileEntity(Long id) {
    return automobileRepository.getAutomobileById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }

  @Override
  public AutomobileDriveAbilityResponse countByDriveAbility() {
    Integer driveable = automobileRepository.countAutomobilesByIsDriveableTrue();
    Integer notDriveable = automobileRepository.countAutomobilesByIsDriveableFalse();
    return new AutomobileDriveAbilityResponse(driveable,notDriveable);
  }

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

  @Override
  public void validateModelIsActive(Model model) {
    if (!model.getIsActive()) {
      throw new ModelNoLongerActiveException("Model is not active anymore. New Automobile can not be created");
    }
  }
}