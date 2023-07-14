package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.ProducerMapper;
import com.example.automobilerestapiapp.models.Producer;
import com.example.automobilerestapiapp.repositories.ProducerRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService{

  private final ProducerRepository producerRepository;
  @Autowired
  public ProducerServiceImpl(ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  @Override
  public List<ProducerResponse> getAll() {
    List<ProducerResponse> producersResponse = new ArrayList<>();
    producerRepository.findAll().forEach(p -> producersResponse.add(ProducerMapper.toProdResponse(p)));
    return producersResponse;
  }

  @Override
  public ProducerResponse getById(Long id) {
    Producer producer = producerRepository.getProducerById(id).orElseThrow(() -> new RecordNotFoundException(id));
    return ProducerMapper.toProdResponse(producer);
  }

  @Override
  public ProducerResponse deleteById(Long id) {
    if (!producerRepository.existsById(id)) {
      throw new RecordNotFoundException(id);
    }
    return ProducerMapper.toProdResponse(producerRepository.deleteProducerById(id));
  }

  @Override
  public ProducerResponse insert(StoreProducerRequest newProducer) {
    Producer producer = producerRepository.save(ProducerMapper.fromStoreProdRequest(newProducer));
    return ProducerMapper.toProdResponse(producer);
  }

  @Override
  public ProducerResponse updateOrSaveNew(StoreProducerRequest newProducer, Long id) {
    Producer producer = producerRepository.getProducerById(id)
        .map(prod -> {
          prod.setName(newProducer.getName());
          prod.setStreet(newProducer.getStreet());
          prod.setCity(newProducer.getCity());
          prod.setZipCode(newProducer.getZipCode());
          prod.setCountry(newProducer.getCountry());

          producerRepository.save(prod);
          return prod;
        })
        .orElseGet(() -> {
          insert(newProducer);
          return ProducerMapper.fromStoreProdRequest(newProducer);
        });
    return ProducerMapper.toProdResponse(producer);
  }
}