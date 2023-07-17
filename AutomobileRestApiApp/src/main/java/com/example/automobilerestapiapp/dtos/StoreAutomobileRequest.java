package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class StoreAutomobileRequest {
  @NotNull(message = "Field 'modelId' can not be empty")
  @Schema(example = "1")
  private Long modelId;
  @NotBlank(message = "Field 'color' can not be empty")
  @Pattern(regexp = "^[a-zA-Z]+$", message = "Can contain only letters")
  @Schema(example = "red", description = "Can contain only letters")
  private String color;
  @NotNull(message = "Field 'performance' can not be empty")
  @Schema(example = "100 or 100.0")
  private Float performance;
  @NotNull(message = "Field 'consumption' can not be empty")
  @Schema(example = "50 or 50.0")
  private Float consumption;
  @NotBlank(message = "Field 'dateOfCreation' can not be empty")
  @Schema(example = "2000-02-10", description = "Has to have valid format yyyy-MM-dd "
          + "and has to fall in range 1886-01-29 to current date (both inclusive)")
  private String dateOfCreation;
  @NotNull(message = "Field 'isDriveable' can not be empty")
  @Schema(example = "true")
  private Boolean isDriveable;
}