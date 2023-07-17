package com.example.automobilerestapiapp.mappers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutomobileMapperTest {

  private Automobile automobile;

  private Model model;
  @BeforeEach
  void init() {
    model = new Model();
    model.setId(1L);
    automobile = new Automobile(model,"red", 50F,60F,
        LocalDate.of(2000,4,28),true);
  }
  @Test
  void toAutomobileResponse() {
    AutomobileResponse dto = AutomobileMapper.toAutomobileResponse(automobile);
    assertEquals(automobile.getModel().getId(), dto.getModelId());
    assertEquals(automobile.getColor(), dto.getColor());
    assertEquals(automobile.getPerformance(), dto.getPerformance());
    assertEquals(automobile.getConsumption(), dto.getConsumption());
    assertEquals(automobile.getDateOfCreation(), dto.getDateOfCreation());
    assertEquals(automobile.getIsDriveable(), dto.getIsDriveable());
  }

  @Test
  void fromStoreAutomobileRequest() {
    StoreAutomobileRequest dto = new StoreAutomobileRequest(1L,"red", 50F,60F,
        "2000-04-28",true);
    LocalDate date = LocalDate.of(2000,4,28);
    automobile = AutomobileMapper.fromStoreAutomobileRequest(dto,date,model);
    assertEquals(automobile.getModel().getId(), dto.getModelId());
    assertEquals(automobile.getColor(), dto.getColor());
    assertEquals(automobile.getPerformance(), dto.getPerformance());
    assertEquals(automobile.getConsumption(), dto.getConsumption());
    assertEquals(automobile.getDateOfCreation(), date);
    assertEquals(automobile.getIsDriveable(), dto.getIsDriveable());
  }
}