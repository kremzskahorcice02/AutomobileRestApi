package com.example.automobilerestapiapp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Model {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  @ManyToOne
  @JoinColumn(name="producer_id", nullable=false)
  private Producer producer;
  private String name;
  private String category;
  private Long minPrice;
  private Long maxPrice;
  private Integer releaseYear;
  private Boolean isActive;
  @OneToMany(fetch = FetchType.LAZY, mappedBy="model")
  private List<Automobile> mobilesManufactured;

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
}