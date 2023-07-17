package com.example.automobilerestapiapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModelResponse {
  private Long id;
  private Long producerId;
  private String name;
  private String category;
  private Long minPrice;
  private Long maxPrice;
  private Integer releaseYear;
  private Boolean isActive;
}