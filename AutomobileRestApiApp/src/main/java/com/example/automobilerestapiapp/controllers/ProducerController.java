package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.services.ProducerService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class ProducerController {

  private final ProducerService producerService;
  @Autowired
  public ProducerController(ProducerService producerService) {
    this.producerService = producerService;
  }
  @GetMapping
  public ResponseEntity<List<ProducerResponse>> getAllProducers() {
    return ResponseEntity.ok().body(producerService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<ProducerResponse> getProducerById(@PathVariable Long id) {
    return ResponseEntity.ok().body(producerService.getById(id));
  }

  @PostMapping(consumes = {"application/json"})
  public ResponseEntity<ProducerResponse> addProducer(@RequestBody @Valid StoreProducerRequest producerDto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(producerService.insert(producerDto));
  }

  @PutMapping(value = "/{id}", consumes = {"application/json"})
  public ResponseEntity<ProducerResponse> replaceOrSaveNewProducer(
      @RequestBody @Valid StoreProducerRequest newProdDto,
      @PathVariable Long id) {
    return ResponseEntity.ok().body(producerService.updateOrSaveNew(newProdDto,id));
  }

  @DeleteMapping("producers/{id}")
  public ResponseEntity<ProducerResponse> deleteProducer(@PathVariable Long id) {
    return ResponseEntity.ok().body(producerService.deleteById(id));
  }
}