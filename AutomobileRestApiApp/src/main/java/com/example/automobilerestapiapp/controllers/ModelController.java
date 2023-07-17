package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.services.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
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
@Tag(name = "Models")
public class ModelController {
  private final ModelService modelService;
  @Autowired
  public ModelController(ModelService modelService) {
    this.modelService = modelService;
  }

  @GetMapping
  @Operation(summary = "Get all models")
  public ResponseEntity<List<ModelResponse>> getModels() {
    List<ModelResponse> models = modelService.getAllModels();
    return ResponseEntity.ok().body(models);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a model by its id")
  public ResponseEntity<ModelResponse> getModelById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(modelService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "Insert new model")
  public ResponseEntity<ModelResponse> insertNewModel(@RequestBody @Valid StoreModelRequest modelDto) {
    return ResponseEntity.status(201).body(modelService.insert(modelDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  @Operation(summary = "Update a model by its id")
  public ResponseEntity<ModelResponse> updateModelOrInsertNew(@RequestBody @Valid StoreModelRequest modelDto,
      @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(modelService.updateOrSaveNew(modelDto,id));
  }
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a model by its id")
  public ResponseEntity<ModelResponse> deleteModelById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(modelService.deleteById(id));
  }
}