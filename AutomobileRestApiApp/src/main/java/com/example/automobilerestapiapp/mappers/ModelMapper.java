package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;

public class ModelMapper {

  public static ModelResponse toModelResponse(Model model) {
    return new ModelResponse(
        model.getId(),
        model.getProducer().getId(),
        model.getName(),
        model.getCategory(),
        model.getMinPrice(),
        model.getMaxPrice(),
        model.getReleaseYear(),
        model.getIsActive()
    );
  }

  public static Model fromStoreModelRequest(StoreModelRequest modelRequest, Producer producer) {
    return new Model(
        producer,
        modelRequest.getName(),
        modelRequest.getCategory(),
        modelRequest.getMinPrice(),
        modelRequest.getMaxPrice(),
        modelRequest.getReleaseYear(),
        modelRequest.getIsActive()
    );
  }
}
