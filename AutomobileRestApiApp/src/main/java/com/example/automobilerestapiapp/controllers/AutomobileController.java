package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.services.AutomobileService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/automobiles")
public class AutomobileController {

  private final AutomobileService automobileService;

  @Autowired
  public AutomobileController(AutomobileService automobileService) {
    this.automobileService = automobileService;
  }

  @GetMapping
  public ResponseEntity<List<AutomobileResponse>> getModels() {
    List<AutomobileResponse> allAutomobiles = automobileService.getAll();
    return ResponseEntity.ok().body(allAutomobiles);
  }

  @GetMapping("/{id}")
  public ResponseEntity<AutomobileResponse> getModelById(@PathVariable Long id) {
    return ResponseEntity.ok().body(automobileService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  public ResponseEntity<AutomobileResponse> insertNewModel(@RequestBody @Valid StoreAutomobileRequest autoDto) {
    return ResponseEntity.ok().body(automobileService.insert(autoDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  public ResponseEntity<AutomobileResponse> updateModelOrInsertNew(@RequestBody @Valid StoreAutomobileRequest autoDto,
      @PathVariable Long id) {
    return ResponseEntity.ok().body(automobileService.updateOrInsertNew(autoDto, id));
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<AutomobileResponse> deleteModelById(@PathVariable Long id) {
    return ResponseEntity.ok().body(automobileService.deleteById(id));
  }
}