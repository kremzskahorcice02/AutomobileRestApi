package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.services.ModelService;
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
@RequestMapping(value = "/api/models", produces = {"application/json"})
public class ModelController {
  private final ModelService modelService;
  @Autowired
  public ModelController(ModelService modelService) {
    this.modelService = modelService;
  }

  @GetMapping
  public ResponseEntity<List<ModelResponse>> getModels() {
    List<ModelResponse> models = modelService.getAllModels();
    return ResponseEntity.ok().body(models);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ModelResponse> getModelById(@PathVariable Long id) {
    return ResponseEntity.ok().body(modelService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  public ResponseEntity<ModelResponse> insertNewModel(@RequestBody @Valid StoreModelRequest modelDto) {
    return ResponseEntity.ok().body(modelService.insert(modelDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  public ResponseEntity<ModelResponse> updateModelOrInsertNew(@RequestBody @Valid StoreModelRequest modelDto,
      @PathVariable Long id) {
    return ResponseEntity.ok().body(modelService.updateOrSaveNew(modelDto,id));
  }
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteModelById(@PathVariable Long id) {
    return ResponseEntity.ok().body(modelService.deleteById(id));
  }
}