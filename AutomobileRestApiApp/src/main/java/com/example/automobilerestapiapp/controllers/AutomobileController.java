package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.services.AutomobileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Automobiles")
public class AutomobileController {

  private final AutomobileService automobileService;

  @Autowired
  public AutomobileController(AutomobileService automobileService) {
    this.automobileService = automobileService;
  }

  @GetMapping
  @Operation(summary = "Get all automobiles")
  public ResponseEntity<List<AutomobileResponse>> getModels() {
    List<AutomobileResponse> allAutomobiles = automobileService.getAll();
    return ResponseEntity.ok().body(allAutomobiles);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get an automobile by its id")
  public ResponseEntity<AutomobileResponse> getModelById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "Insert new automobile")
  public ResponseEntity<AutomobileResponse> insertNewModel(@RequestBody @Valid StoreAutomobileRequest autoDto) {
    return ResponseEntity.status(201).body(automobileService.insert(autoDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  @Operation(summary = "Update an automobile by its id")
  public ResponseEntity<AutomobileResponse> updateModelOrInsertNew(@RequestBody @Valid StoreAutomobileRequest autoDto,
      @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.updateOrInsertNew(autoDto, id));
  }
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an automobile by its id")
  public ResponseEntity<AutomobileResponse> deleteModelById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.deleteById(id));
  }
}