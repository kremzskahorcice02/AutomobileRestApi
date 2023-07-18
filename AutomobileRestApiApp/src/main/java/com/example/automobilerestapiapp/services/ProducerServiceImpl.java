package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.ProducerMapper;
import com.example.automobilerestapiapp.models.Log;
import com.example.automobilerestapiapp.models.Producer;
import com.example.automobilerestapiapp.repositories.ProducerRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProducerServiceImpl implements ProducerService{

  private final ProducerRepository producerRepository;

  private final LogService logService;
  @Autowired
  public ProducerServiceImpl(ProducerRepository producerRepository, LogService logService) {
    this.producerRepository = producerRepository;
    this.logService = logService;
  }

  @Override
  public List<ProducerResponse> getAll() {
    List<ProducerResponse> producersResponse = new ArrayList<>();
    producerRepository.findAll().forEach(p -> producersResponse.add(ProducerMapper.toProdResponse(p)));
    return producersResponse;
  }

  @Override
  public ProducerResponse getById(Long id) {
    Producer producer = getProducerEntity(id);
    return ProducerMapper.toProdResponse(producer);
  }

  @Override
  public ProducerResponse insert(StoreProducerRequest updatedProducer) {
    Producer producer = producerRepository.save(ProducerMapper.fromStoreProdRequest(updatedProducer));
    logService.log(new Log().success().setMessage("New Producer created"));
    return ProducerMapper.toProdResponse(producer);
  }

  @Override
  public ProducerResponse updateOrSaveNew(StoreProducerRequest updatedProducer, Long id) {
    return producerRepository.getProducerById(id)
        .map(prod -> {
          prod.setNewProperties(updatedProducer);
          producerRepository.save(prod);
          logService.log(new Log().success().setMessage("Producer updated"));
          return ProducerMapper.toProdResponse(prod);
        })
        .orElseGet(() -> insert(updatedProducer));
  }

  @Override
  public ProducerResponse deleteById(Long id) {
    Producer producer = getProducerEntity(id);
    producerRepository.delete(producer);
    logService.log(new Log().success().setMessage("Producer deleted"));
    return ProducerMapper.toProdResponse(producer);
  }

  @Override
  public Producer getProducerEntity(Long id) {
    return producerRepository.getProducerById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }
}