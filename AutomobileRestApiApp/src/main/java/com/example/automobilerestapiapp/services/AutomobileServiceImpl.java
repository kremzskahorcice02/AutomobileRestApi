package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.exceptions.InvalidDateException;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.AutomobileMapper;
import com.example.automobilerestapiapp.mappers.ModelMapper;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;
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
  public AutomobileResponse insert(StoreAutomobileRequest newAuto) {
      LocalDate dateOfCreation = validateDateOfCreation(newAuto.getDateOfCreation());
      Model model = modelService.getModelEntity(newAuto.getModelId());
      Automobile auto = AutomobileMapper.fromStoreAutomobileRequest(newAuto,dateOfCreation,model);
      model.addNewAutomobile(auto);
      modelRepository.save(model);
      automobileRepository.save(auto);
      return AutomobileMapper.toAutomobileResponse(auto);
  }

  @Override
  public AutomobileResponse updateOrInsertNew(StoreAutomobileRequest newAuto, Long id) {
    return automobileRepository.getAutomobileById(id)
        .map(auto -> {

          Long newModelId = newAuto.getModelId();
          Model model;
          if (!newModelId.equals(auto.getModel().getId())) {
            model = modelService.getModelEntity(newModelId);
            Model oldModel = auto.getModel();
            oldModel.removeAutomobile(auto);
            model.addNewAutomobile(auto);
          } else {
            model = auto.getModel();
          }
          LocalDate newDateOfCreation = validateDateOfCreation(newAuto.getDateOfCreation());
          auto.setNewProperties(newAuto, model, newDateOfCreation);
          automobileRepository.save(auto);
          modelRepository.save(model);

          return AutomobileMapper.toAutomobileResponse(auto);
        })
        .orElseGet(() -> insert(newAuto));
  }

  @Override
  public AutomobileResponse deleteById(Long id) {
    Automobile automobile = getAutomobileEntity(id);
    automobileRepository.delete(automobile);
    return AutomobileMapper.toAutomobileResponse(automobile);
  }

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