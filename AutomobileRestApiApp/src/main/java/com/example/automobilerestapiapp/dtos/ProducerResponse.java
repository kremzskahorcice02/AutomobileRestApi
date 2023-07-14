package com.example.automobilerestapiapp.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class ProducerResponse {
  private Long Id;
  private String name;
  private String street;
  private String city;
  private String zipCode;
  private String country;
}
