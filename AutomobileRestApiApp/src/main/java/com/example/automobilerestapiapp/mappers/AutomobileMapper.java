package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.models.Model;
import java.time.LocalDate;

public class AutomobileMapper {

  /**
   * converts automobile object to the response dto for displaying to users
   * @param automobile automobile object to be converted
   * @return response dto of the automobile object
   */
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

  /**
   * converts automobile dto from user input to new automobile object
   * @param newAuto dto of the automobile object to be stored
   * @param model model object to establish the manyToOne relationship
   * @param dateOfCreation localDate object to be stored in automobile object's 'dateOfCreation' field
   * @return automobile object created from the dto
   */
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