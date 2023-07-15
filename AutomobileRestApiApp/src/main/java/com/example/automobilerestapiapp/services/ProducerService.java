package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Producer;
import java.util.List;

public interface ProducerService {

  List<ProducerResponse> getAll();
  ProducerResponse getById(Long id);
  ProducerResponse insert(StoreProducerRequest producer);
  ProducerResponse updateOrSaveNew(StoreProducerRequest newProd, Long id);
  ProducerResponse deleteById(Long id);
  Producer getProducerEntity(Long id);
}
