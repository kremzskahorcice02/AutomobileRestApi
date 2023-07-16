package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.models.Automobile;
import java.time.LocalDate;
import java.util.List;

public interface AutomobileService {

  List<AutomobileResponse> getAll();

  AutomobileResponse getById(Long id);

  AutomobileResponse insert(StoreAutomobileRequest newAuto);

  AutomobileResponse updateOrInsertNew(StoreAutomobileRequest newAuto, Long id);

  AutomobileResponse deleteById(Long id);

  Automobile getAutomobileEntity(Long id);

  LocalDate validateDateOfCreation(String date);
}