package com.example.automobilerestapiapp.dtos;

import com.example.automobilerestapiapp.models.Model;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class AutomobileResponse {
  private Long Id;
  private Long modelId;
  private String color;
  private Float performance;
  private Float consumption;
  private LocalDate dateOfCreation;
  private Boolean isDriveable;
}
