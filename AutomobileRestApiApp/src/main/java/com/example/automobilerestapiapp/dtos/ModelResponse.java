package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelResponse {

  @Schema(example = "1")
  private Long id;
  @Schema(example = "1")
  private Long producerId;
  @Schema(example = "A‑Class")
  private String name;
  @Schema(example = "SUV")
  private String category;
  @Schema(example = "10000")
  private Long minPrice;
  @Schema(example = "300000")
  private Long maxPrice;
  @Schema(example = "2000")
  private Integer releaseYear;
  @Schema(example = "true")
  private Boolean isActive;
}