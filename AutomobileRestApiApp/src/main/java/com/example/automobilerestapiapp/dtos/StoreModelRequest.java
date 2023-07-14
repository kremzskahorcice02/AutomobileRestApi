package com.example.automobilerestapiapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StoreModelRequest {
  @NotNull(message = "Field 'producerId' can not be empty")
  private Long producerId;

  @NotBlank(message = "Field 'name' can not be empty")
  @Pattern(regexp = ".*[a-zA-Z]+.*",
      message = "Field 'street' has to contain letter")
  private String name;
  @NotBlank(message = "Field 'category' can not be empty")
  @Pattern(regexp = ".*[a-zA-Z]+.*",
      message = "Field 'street' has to contain letter")
  private String category;
  @NotNull(message = "Field 'minPrice' can not be empty")
  private Long minPrice;
  @NotNull(message = "Field 'maxPrice' can not be empty")
  private Long maxPrice;
  @NotNull(message = "Field 'releaseYear' can not be empty")
  private Integer releaseYear;

  @NotNull(message = "Field 'isActive' can not be empty")
  private Boolean isActive;
}
