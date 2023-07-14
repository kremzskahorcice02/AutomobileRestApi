package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Producer;

public class ProducerMapper {
  public static ProducerResponse toProdResponse(Producer producer) {
    return new ProducerResponse(
        producer.getId(),
        producer.getName(),
        producer.getStreet(),
        producer.getCity(),
        producer.getZipCode(),
        producer.getCountry()
    );
  }

  public static Producer fromStoreProdRequest(StoreProducerRequest producerRequest) {
   return new Producer(
       producerRequest.getName(),
       producerRequest.getStreet(),
       producerRequest.getCity(),
       producerRequest.getZipCode(),
       producerRequest.getCountry()
   );
  }
}