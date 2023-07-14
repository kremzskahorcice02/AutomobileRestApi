package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Producer;
import java.util.List;
import java.util.Optional;

public interface ProducerService {

  List<ProducerResponse> getAll();
  ProducerResponse getById(Long id);
  ProducerResponse deleteById(Long id);
  ProducerResponse insert(StoreProducerRequest producer);
  ProducerResponse updateOrSaveNew(StoreProducerRequest newProd, Long id);
}
