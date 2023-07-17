package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;

public class ModelMapper {

  /**
   * converts model object to the response dto for displaying to users
   * @param model model object to be converted
   * @return response dto of the model object
   */
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

  /**
   * converts model dto from user input to new model object
   * @param modelRequest dto of the model object to be stored
   * @param producer producer object to establish the manyToOne relationship
   * @return model object created from the dto
   */
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
