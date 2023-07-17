package com.example.automobilerestapiapp.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ModelMapperTest {

  private Model model;

  private Producer producer;
  @BeforeEach
  void init() {
    producer = new Producer();
    producer.setId(1L);
    model = new Model(producer,"1KV","SUV",1500L, 3000L, 2000,true);
  }
  @Test
  void toModelResponse() {
    ModelResponse dto = ModelMapper.toModelResponse(model);
    assertEquals(model.getProducer().getId(), dto.getProducerId());
    assertEquals(model.getName(), dto.getName());
    assertEquals(model.getMinPrice(), dto.getMinPrice());
    assertEquals(model.getMaxPrice(),dto.getMaxPrice());
    assertEquals(model.getReleaseYear(),dto.getReleaseYear());
    assertEquals(model.getIsActive(), dto.getIsActive());
  }

  @Test
  void fromStoreModelRequest() {
    StoreModelRequest dto = new StoreModelRequest(producer.getId(),"1KV","SUV",1500L, 3000L, 2000,true);
    assertEquals(model.getProducer().getId(), dto.getProducerId());
    assertEquals(model.getName(), dto.getName());
    assertEquals(model.getMinPrice(), dto.getMinPrice());
    assertEquals(model.getMaxPrice(),dto.getMaxPrice());
    assertEquals(model.getReleaseYear(),dto.getReleaseYear());
    assertEquals(model.getIsActive(), dto.getIsActive());
  }
}