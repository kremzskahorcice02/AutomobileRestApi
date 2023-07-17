package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
  @Schema(example = "Description of the problem")
  private String error;
}
