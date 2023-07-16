package com.example.automobilerestapiapp.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class StoreAutomobileRequest {
  @NotNull(message = "Field 'modelId' can not be empty")
  private Long modelId;
  @NotBlank(message = "Field 'color' can not be empty")
  private String color;
  @NotNull(message = "Field 'performance' can not be empty")
  private Float performance;
  @NotNull(message = "Field 'consumption' can not be empty")
  private Float consumption;
  @NotBlank(message = "Field 'dateOfCreation' can not be empty")
  @Pattern(regexp = "^[0-9-]+$", message = "Field 'dateOfCreation' has to have valid format: yyyy-MM-dd")
  private String dateOfCreation;
  @NotNull(message = "Field 'isDriveable' can not be empty")
  private Boolean isDriveable;
}