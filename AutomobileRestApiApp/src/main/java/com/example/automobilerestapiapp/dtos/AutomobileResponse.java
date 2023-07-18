package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AutomobileResponse {
  @Schema(example = "1")
  private Long Id;
  @Schema(example = "1")
  private Long modelId;
  @Schema(example = "red")
  private String color;
  @Schema(example = "100.0")
  private Float performance;
  @Schema(example = "50.0")
  private Float consumption;
  @Schema(example = "2000-02-10")
  private LocalDate dateOfCreation;
  @Schema(example = "true")
  private Boolean isDriveable;
}
