package com.example.automobilerestapiapp.mappers;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Producer;

public class ProducerMapper {
  /**
   * converts producer object to the response dto for displaying to users
   * @param producer producer object to be converted
   * @return response dto of the producer object
   */
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

  /**
   * converts producer dto from user input to new producer object
   * @param producerRequest dto of the producer object to be stored
   * @return producer object created from the dto
   */
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