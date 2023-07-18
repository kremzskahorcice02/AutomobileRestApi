package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogResponse {

  @Schema(example = "INFO")
  private String level;
  @Schema(example = "2023-07-18T07:27:40.721599")
  private LocalDateTime timestamp;

  @Schema(example = "GET /api/automobiles")
  private String message;
}
