package com.example.automobilerestapiapp.models;

import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Automobile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @ManyToOne
  @JoinColumn(name="model_id", nullable=false)
  private Model model;
  private String color;
  private Float performance;
  private Float consumption;
  private LocalDate dateOfCreation;
  private Boolean isDriveable;

  public Automobile(Model model, String color, Float performance, Float consumption,
      LocalDate dateOfCreation, Boolean isDriveable) {
    this.model = model;
    this.color = color;
    this.performance = performance;
    this.consumption = consumption;
    this.dateOfCreation = dateOfCreation;
    this.isDriveable = isDriveable;
  }

  public void setNewProperties(StoreAutomobileRequest newData, Model model, LocalDate newDateOfCreation) {
    this.model = model;
    this.color = newData.getColor();
    this.performance = newData.getPerformance();
    this.consumption = newData.getConsumption();
    this.dateOfCreation = newDateOfCreation;
    this.isDriveable = newData.getIsDriveable();
  }
}