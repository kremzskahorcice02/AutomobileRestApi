package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.ErrorResponse;
import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Log;
import com.example.automobilerestapiapp.services.LogService;
import com.example.automobilerestapiapp.services.ProducerService;
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
@RequestMapping(value = "/api/producers", produces = {"application/json"})
@Tag(name = "Producers")
public class ProducerController {

  private final ProducerService producerService;

  private final LogService logService;
  @Autowired
  public ProducerController(ProducerService producerService, LogService logService) {
    this.producerService = producerService;
    this.logService = logService;
  }
  @GetMapping
  @Operation(summary = "Get all producers")
  @ApiResponses(value = {@ApiResponse(responseCode = "200",description = "Success")})
  public ResponseEntity<List<ProducerResponse>> getAllProducers() {
    logService.log(new Log().info().endpointRequest("/api/automobiles", "GET"));
    return ResponseEntity.ok().body(producerService.getAll());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get a producer by its id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",
          description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Producer of given id was not found",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<ProducerResponse> getProducerById(@PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "GET"));
    return ResponseEntity.ok().body(producerService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  @Operation(summary = "Insert new producer")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input data",content = @Content(
          mediaType = "application/json",
          array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class))))
  })
  public ResponseEntity<ProducerResponse> addProducer(@RequestBody @Valid StoreProducerRequest producerDto) {
    logService.log(new Log().info().endpointRequest("/api/automobiles", "POST"));
    return ResponseEntity.status(201).body(producerService.insert(producerDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  @Operation(summary = "Update existing producer by its id (or insert new if does not exist)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "400", description = "Wrong input data",
                  content = @Content(array = @ArraySchema(schema = @Schema(implementation = ErrorResponse.class)))),
      @ApiResponse(responseCode = "404",
          description = "Producer of given id was not found",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
  public ResponseEntity<ProducerResponse> replaceOrSaveNewProducer(
      @RequestBody @Valid StoreProducerRequest newProdDto,
      @PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "PUT"));
    return ResponseEntity.ok().body(producerService.updateOrSaveNew(newProdDto,id));
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a producer by id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200",description = "Success"),
      @ApiResponse(responseCode = "404",
          description = "Producer of given id was not found",
          content = @Content(
              schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<ProducerResponse> deleteProducer(@PathVariable("id") Long id) {
    logService.log(new Log().info().endpointRequest("/api/automobiles/{id}", "DELETE"));
    return ResponseEntity.ok().body(producerService.deleteById(id));
  }
}