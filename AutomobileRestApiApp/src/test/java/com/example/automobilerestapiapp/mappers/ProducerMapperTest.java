package com.example.automobilerestapiapp.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.models.Model;
import com.example.automobilerestapiapp.models.Producer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProducerMapperTest {

  private Producer producer;

  @BeforeEach
  void init() {
    producer = new Producer("BMW Group", "Main Street", "Frankfurt", "37001", "Germany");
    producer.setId(1L);
  }
  @Test
  void toProdResponse() {
    ProducerResponse dto = ProducerMapper.toProdResponse(producer);
    dto.setId(1L);
    assertEquals(producer.getId(), dto.getId());
    assertEquals(producer.getName(), dto.getName());
    assertEquals(producer.getStreet(), dto.getStreet());
    assertEquals(producer.getCity(), dto.getCity());
    assertEquals(producer.getZipCode(),dto.getZipCode());
    assertEquals(producer.getCountry(),dto.getCountry());
  }

  @Test
  void fromStoreProdRequest() {
    StoreProducerRequest dto = new StoreProducerRequest("BMW Group", "Main Street", "Frankfurt", "37001", "Germany");
    producer = ProducerMapper.fromStoreProdRequest(dto);
    assertEquals(producer.getName(), dto.getName());
    assertEquals(producer.getStreet(), dto.getStreet());
    assertEquals(producer.getCity(), dto.getCity());
    assertEquals(producer.getZipCode(),dto.getZipCode());
    assertEquals(producer.getCountry(),dto.getCountry());

  }
}