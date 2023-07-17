package com.example.automobilerestapiapp.services;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.mappers.ProducerMapper;
import com.example.automobilerestapiapp.models.Producer;
import com.example.automobilerestapiapp.repositories.ProducerRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class ProducerServiceImplTest {

  @Mock
  private ProducerRepository producerRepository;

  private ProducerResponse producerDto;

  @InjectMocks
  private ProducerService producerService;

  @BeforeEach
  void init() {
    producerDto = new ProducerResponse(1L,"BMW Group", "Main street",
        "Frankfurt", "37001", "Germany");
  }

  @Test
  void get_producer_by_id_throws_exception() {
    Producer producer = new Producer("BMW Group", "",
        "Frankfurt", "37001", "Germany");
    given(producerService.getProducerEntity(1L)).willReturn(producer);
    Producer actual = producerService.getProducerEntity(1L);
  }

  @Test
  void insert() {
  }

  @Test
  void updateOrSaveNew() {
  }

  @Test
  void deleteById() {
  }

  @Test
  void getProducerEntity() {
  }
}