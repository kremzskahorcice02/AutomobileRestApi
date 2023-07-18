package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.ErrorResponse;
import com.example.automobilerestapiapp.dtos.ModelResponse;
import com.example.automobilerestapiapp.dtos.StoreModelRequest;
import com.example.automobilerestapiapp.models.Log;
import com.example.automobilerestapiapp.services.LogService;
import com.example.automobilerestapiapp.services.ModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/api/models", produces = {"application/json"})
@Tag(name = "Models")
public class ModelController {
  private final ModelService modelService;

  private final LogService logService;
  @Autowired
  public ModelController(ModelService modelService, LogService logService) {
    this.modelService = modelService;
    this.logService = logService;
  }

  @GetMapping
  @Operation(summary = "Get all models")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success")})
  public ResponseEntity<List<ModelResponse>> getModels() {
    logService.log(new Log().info().endpointRequest("/api/automobiles", "GET"));
    List<ModelResponse> models = modelService.getAllModels();
    return ResponseEntity.ok().body(models);
  }
  @GetMapping("/{id}")
  @Operation(summary = "Get a model by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Model of given id was not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })

  public ResponseEntity<ModelResponse> getModelById(@PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "GET"));
    return ResponseEntity.ok().body(modelService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "Insert new model")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input data",content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
      @ApiResponse(responseCode = "404",
          description = "Producer with id of 'producerId' value was not found",
          content = @Content(
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<ModelResponse> insertNewModel(@RequestBody @Valid StoreModelRequest modelDto) {
    logService.log(new Log().info().endpointRequest("/api/automobiles", "POST"));
    return ResponseEntity.status(201).body(modelService.insert(modelDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  @Operation(summary = "Update a model by its id (or insert new if does not exist)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input data",
          content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
      @ApiResponse(responseCode = "404",
          description = "Model of given id was not found or producer with id of 'producerId' value was not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  public ResponseEntity<ModelResponse> updateModelOrInsertNew(@RequestBody @Valid StoreModelRequest modelDto,
      @PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "PUT"));
    return ResponseEntity.ok().body(modelService.updateOrSaveNew(modelDto,id));
  }
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a model by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Model of given id was not found",
          content = @Content(
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<ModelResponse> deleteModelById(@PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "DELETE"));
    return ResponseEntity.ok().body(modelService.deleteById(id));
  }
}