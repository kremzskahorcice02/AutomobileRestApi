package com.example.automobilerestapiapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class StoreAutomobileRequest {
  @NotNull(message = "Field 'modelId' can not be empty")
  private Long modelId;
  @NotBlank(message = "Field 'color' can not be empty")
  @Pattern(regexp = "^[a-zA-Z]+$", message = "Field 'color' can contain only letters")
  private String color;
  @NotNull(message = "Field 'performance' can not be empty")
  private Float performance;
  @NotNull(message = "Field 'consumption' can not be empty")
  private Float consumption;
  @NotBlank(message = "Field 'dateOfCreation' can not be empty")
  private String dateOfCreation;
  @NotNull(message = "Field 'isDriveable' can not be empty")
  private Boolean isDriveable;
}