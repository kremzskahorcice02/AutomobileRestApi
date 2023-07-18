package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProducerResponse {
  @Schema(example = "1")
  private Long Id;
  @Schema(example = "Toyota")
  private String name;
  @Schema(example = "28. října 130/50")
  private String street;
  @Schema(example = "České Budějovice")
  private String city;
  @Schema(example = "37001")
  private String zipCode;
  @Schema(example = "Slovenia")
  private String country;
}
