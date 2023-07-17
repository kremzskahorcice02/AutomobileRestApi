package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.models.Automobile;
import java.time.LocalDate;
import java.util.List;

public interface AutomobileService {

  List<AutomobileResponse> getAll();

  /**
   * Fetches an automobile object from repo of given id and converts the object to dto.
   * @param id the id of the desired automobile
   * @throws RecordNotFoundException if automobile of given id was not found
   * @return dto of the automobile with given id
   */
  AutomobileResponse getById(Long id);

  AutomobileResponse insert(StoreAutomobileRequest newAuto);

  AutomobileResponse updateOrInsertNew(StoreAutomobileRequest newAuto, Long id);

  AutomobileResponse deleteById(Long id);

  Automobile getAutomobileEntity(Long id);

  LocalDate validateDateOfCreation(String date);
}