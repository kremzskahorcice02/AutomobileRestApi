package com.example.automobilerestapiapp.models;

import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import jakarta.persistence.CascadeType;
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
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Producer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long Id;
  private String name;
  private String street;
  private String city;
  private String zipCode;
  private String country;
  @OneToMany(mappedBy="producer",cascade = CascadeType.REMOVE, orphanRemoval = true)
  private List<Model> releasedModels;

  public Producer(String name, String street, String city, String zipCode, String country) {
    this.name = name;
    this.street = street;
    this.city = city;
    this.zipCode = zipCode;
    this.country = country;
    this.releasedModels = new ArrayList<>();
  }

  public void setNewProperties(StoreProducerRequest newData) {
    this.setName(newData.getName());
    this.setStreet(newData.getStreet());
    this.setCity(newData.getCity());
    this.setZipCode(newData.getZipCode());
    this.setCountry(newData.getCountry());
  }
  public void addNewModel(Model model) {
    this.releasedModels.add(model);
  }

  public void removeModel(Model model) {
    this.releasedModels.remove(model);
  }
}