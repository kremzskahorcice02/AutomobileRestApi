package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import java.time.LocalDate;

public class AutomobileMapper {
  public static AutomobileResponse toAutomobileResponse(Automobile automobile) {
    return new AutomobileResponse(
        automobile.getId(),
        automobile.getModel().getId(),
        automobile.getColor(),
        automobile.getPerformance(),
        automobile.getConsumption(),
        automobile.getDateOfCreation(),
        automobile.getIsDriveable()
    );
  }

  public static Automobile fromStoreAutomobileRequest(StoreAutomobileRequest newAuto, LocalDate dateOfCreation, Model model) {
    return new Automobile(
        model,
        newAuto.getColor(),
        newAuto.getPerformance(),
        newAuto.getConsumption(),
        dateOfCreation,
        newAuto.getIsDriveable()
    );
  }
}