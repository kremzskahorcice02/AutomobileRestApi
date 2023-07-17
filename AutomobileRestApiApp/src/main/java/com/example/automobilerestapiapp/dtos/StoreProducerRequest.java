package com.example.automobilerestapiapp.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreProducerRequest {
    @NotBlank(message = "Field 'name' can not be empty")
    @Schema(example = "Toyota")
    private String name;
    @NotBlank(message = "Field 'street' can not be empty")
    @Pattern(regexp = ".*[a-zA-Z]+.*",
        message = "Field 'street' has to contain at least one letter")
    @Schema(example = "28. října 130/50", description = "Has to contain at least one letter")
    private String street;
    @NotBlank(message = "Field 'city' can not be empty")
    @Pattern(regexp = ".*[a-zA-Z]+.*",
        message = "Field 'city' has to contain at least one letter")
    @Schema(example = "České Budějovice", description = "Has to contain at least one letter")
    private String city;
    @NotNull(message = "Field 'zipCode' can not be empty")
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$",
        message = "Field 'zipCode' should follow one of the common zipCode numeric formats"
            + "[xxxxxx, xxxxx-xxxx, xxxxx xxxx]")
    @Schema(example = "37001", description = "Allowed formats : [xxxxxx, xxxxx-xxxx, xxxxx xxxx]")
    private String zipCode;

    @NotBlank(message = "Field 'country' can not be empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Field 'country' can contain only letters")
    @Schema(example = "Slovenia", description = "Can contain only letters")
    private String country;
}