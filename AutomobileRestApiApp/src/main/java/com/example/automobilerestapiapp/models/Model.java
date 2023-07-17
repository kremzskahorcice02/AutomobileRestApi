package com.example.automobilerestapiapp.models;

import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "models")
public class Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="producer_id", nullable=false)
  private Producer producer;
  private String name;
  private String category;
  private Long minPrice;
  private Long maxPrice;
  private Integer releaseYear;
  private Boolean isActive;
  @OneToMany(mappedBy="model", cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Automobile> mobilesManufactured;

  /**
   * Constructs populated Model object.
   */
  public Model(Producer producer, String name, String category, Long minPrice, Long maxPrice,
      Integer releaseYear, Boolean isActive) {
    this.producer = producer;
    this.name = name;
    this.category = category;
    this.minPrice = minPrice;
    this.maxPrice = maxPrice;
    this.releaseYear = releaseYear;
    this.isActive = isActive;
    this.mobilesManufactured = new ArrayList<>();
  }

  /**
   * Updates data of the Model object
   * @param newData Model dto to update the Model object
   * @param producer Producer object to be added in manyToOne relationship
   */
  public void setNewProperties(StoreModelRequest newData, Producer producer) {
    this.producer = producer;
    this.name = newData.getName();
    this.category = newData.getCategory();
    this.minPrice = newData.getMinPrice();
    this.maxPrice = newData.getMaxPrice();
    this.releaseYear = newData.getReleaseYear();
    this.isActive = newData.getIsActive();
  }

  /**
   * Adds new Automobile object to the Model object in oneToMany relationship
   * @param auto Automobile object to be added
   */
  public void addNewAutomobile(Automobile auto) {
    mobilesManufactured.add(auto);
  }

  /**
   * Removes Automobile object from the Model object in oneToMany relationship
   * @param automobile Automobile object to be removed
   */
  public void removeAutomobile(Automobile automobile) {
    mobilesManufactured.remove(automobile);
  }
}