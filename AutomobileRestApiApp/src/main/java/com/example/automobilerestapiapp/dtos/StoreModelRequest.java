package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreModelRequest {
  @NotNull(message = "Field 'producerId' can not be empty")
  @Schema(example = "1")
  private Long producerId;

  @NotBlank(message = "Field 'name' can not be empty")
  @Pattern(regexp = ".*[a-zA-Z]+.*",
      message = "Field 'name' has to contain at least one letter")
  @Schema(example = "A‑Class", description = "Has to contain at least letter")
  private String name;
  @NotBlank(message = "Field 'category' can not be empty")
  @Pattern(regexp = ".*[a-zA-Z]+.*",
      message = "Field 'category' has to contain at least one letter")
  @Schema(example = "SUV", description = "Has to contain at least one letter")
  private String category;
  @NotNull(message = "Field 'minPrice' can not be empty")
  @Min(value = 1, message = "Field 'minPrice has to be higher or equal to 1")
  @Schema(example = "10000", description = "Has to to be higher or equal to 1")
  private Long minPrice;
  @NotNull(message = "Field 'maxPrice' can not be empty")
  @Min(value = 1, message = "Field 'maxPrice' has to be higher or equal to 1")
  @Schema(example = "300000", description = "Has to to be higher or equal to 1")
  private Long maxPrice;
  @NotNull(message = "Field 'releaseYear' can not be empty")
  @Schema(example = "2000", description = "Has to fall in range 1886 to current date (both inclusive)")
  private Integer releaseYear;

  @NotNull(message = "Field 'isActive' can not be empty")
  @Schema(example = "true")
  private Boolean isActive;
}