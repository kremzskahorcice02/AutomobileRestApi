package com.example.automobilerestapiapp.controllers;

import com.example.automobilerestapiapp.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/producers")
public class ProducerController {

  private final ProducerService producerService;
  @Autowired
  public ProducerController(ProducerService producerService) {
    this.producerService = producerService;
  }
}
