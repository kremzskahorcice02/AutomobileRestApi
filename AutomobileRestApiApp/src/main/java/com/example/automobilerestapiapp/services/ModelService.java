package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Model;
import java.util.List;

public interface ModelService {
  List<ModelResponse> getAllModels();
  ModelResponse getById(Long id);
  ModelResponse insert(StoreModelRequest modelDto);

  ModelResponse updateOrSaveNew(StoreModelRequest model, Long id);
  ModelResponse deleteById(Long id);
  Model getModelEntity(Long id);
  void validateReleaseYear(Integer year);
}