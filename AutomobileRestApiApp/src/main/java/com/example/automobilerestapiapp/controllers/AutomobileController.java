package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.AutomobileResponse;
import com.example.automobilerestapiapp.dtos.ErrorResponse;
import com.example.automobilerestapiapp.dtos.StoreAutomobileRequest;
import com.example.automobilerestapiapp.models.Automobile;
import com.example.automobilerestapiapp.services.AutomobileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.ArrayList;
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
@RequestMapping(value = "api/automobiles", produces = {"application/json"})
@Tag(name = "Automobiles")
public class AutomobileController {

  private final AutomobileService automobileService;

  @Autowired
  public AutomobileController(AutomobileService automobileService) {
    this.automobileService = automobileService;
  }

  @GetMapping
  @Operation(summary = "Get all automobiles")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success")})
  public ResponseEntity<List<AutomobileResponse>> getAutomobile() {
    List<AutomobileResponse> allAutomobiles = automobileService.getAll();
    return ResponseEntity.ok().body(allAutomobiles);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get an automobile by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Automobile of given id was not found",
          content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<AutomobileResponse> getAutomobileById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "Insert new automobile")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input format",content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
      @ApiResponse(responseCode = "404",
          description = "Model with id of 'modelId' value was not found",
          content = @Content(
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<AutomobileResponse> insertNewAutomobile(
      @RequestBody @Valid StoreAutomobileRequest autoDto) {
    return ResponseEntity.status(201).body(automobileService.insert(autoDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  @Operation(summary = "Update an automobile by its id (or insert new if does not exist)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input format",
      content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
      @ApiResponse(responseCode = "404",
          description = "Automobile of given id was not found or model with id of 'modelId' value was not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  public ResponseEntity<AutomobileResponse> updateAutomobileOrInsertNew(
      @RequestBody @Valid StoreAutomobileRequest autoDto,
      @PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.updateOrInsertNew(autoDto, id));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete an automobile by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Automobile of given id was not found",
          content = @Content(
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<AutomobileResponse> deleteAutomobileById(@PathVariable("id") Long id) {
    return ResponseEntity.ok().body(automobileService.deleteById(id));
  }
}