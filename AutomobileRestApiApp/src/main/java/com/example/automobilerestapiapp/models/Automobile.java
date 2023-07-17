package com.example.automobilerestapiapp.models;

import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "automobile")
public class Automobile {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="model_id", nullable=false)
  private Model model;
  private String color;
  private Float performance;
  private Float consumption;
  private LocalDate dateOfCreation;
  private Boolean isDriveable;

  /**
   * Constructs populated Automobile object
   */
  public Automobile(Model model, String color, Float performance, Float consumption,
      LocalDate dateOfCreation, Boolean isDriveable) {
    this.model = model;
    this.color = color;
    this.performance = performance;
    this.consumption = consumption;
    this.dateOfCreation = dateOfCreation;
    this.isDriveable = isDriveable;
  }

  /**
   * Updates data of the Automobile object
   * @param newData Automobile dto to update the Automobile object
   * @param model Model object to be added in manyToOne relationship
   * @param newDateOfCreation localDate value to update the 'dateOfCreation' field of the Automobile
   */
  public void setNewProperties(StoreAutomobileRequest newData, Model model, LocalDate newDateOfCreation) {
    this.model = model;
    this.color = newData.getColor();
    this.performance = newData.getPerformance();
    this.consumption = newData.getConsumption();
    this.dateOfCreation = newDateOfCreation;
    this.isDriveable = newData.getIsDriveable();
  }
}