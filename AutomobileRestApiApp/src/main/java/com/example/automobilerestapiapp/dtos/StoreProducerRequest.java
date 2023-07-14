package com.example.automobilerestapiapp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class StoreProducerRequest {
    @NotBlank(message = "Field 'name' can not be empty")
    private String name;
    @NotBlank(message = "Field 'street' can not be empty")
    @Pattern(regexp = ".*[a-zA-Z]+.*",
        message = "Field 'street' has to contain letter")
    private String street;
    @NotBlank(message = "Field 'city' can not be empty")
    @Pattern(regexp = ".*[a-zA-Z]+.*",
        message = "Field 'city' has to contain letter")
    private String city;
    @NotNull(message = "Field 'zipCode' can not be empty")
    @Pattern(regexp = "^\\d{5}(?:[-\\s]\\d{4})?$",
        message = "Field 'zipCode' should follow one of the common zipCode numeric formats "
            + "[xxxxxx, xxxxx-xxxx, xxxxx xxxx]")
    private String zipCode;
    @NotBlank(message = "Field 'country' can not be empty")
    private String country;
}