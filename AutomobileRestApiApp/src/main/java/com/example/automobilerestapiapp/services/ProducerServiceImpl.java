package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.mappers.ProducerMapper;
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
  @Autowired
  public ProducerServiceImpl(ProducerRepository producerRepository) {
    this.producerRepository = producerRepository;
  }

  /**
   * Fetches all producer objects from repo and converts the objects to dtos.
   * @return arraylist of producer dtos
   */
  @Override
  public List<ProducerResponse> getAll() {
    List<ProducerResponse> producersResponse = new ArrayList<>();
    producerRepository.findAll().forEach(p -> producersResponse.add(ProducerMapper.toProdResponse(p)));
    return producersResponse;
  }


  /**
   * Fetches a producer object from repo of given id and converts the object to dto.
   * @param id the id of the desired producer
   * @throws RecordNotFoundException if producer of given id was not found
   * @return dto of the producer with given id
   */
  @Override
  public ProducerResponse getById(Long id) {
    Producer producer = getProducerEntity(id);
    return ProducerMapper.toProdResponse(producer);
  }

  /**
   * Creates new producer object from dto and saves it in a database
   * @param updatedProducer dto object with data to create new producer entity
   * @return dto of the producer that was created
   */
  @Override
  public ProducerResponse insert(StoreProducerRequest updatedProducer) {
    Producer producer = producerRepository.save(ProducerMapper.fromStoreProdRequest(updatedProducer));
    return ProducerMapper.toProdResponse(producer);
  }

  /**
   * Serves for updating producer object stored in database. It fetches model of given id and updates
   * all the fields respectively to the new data object passed as an argument.
   * If model of given id was not found it calls the {@link #insert(StoreProducerRequest)} method to create
   * completely new producer object and to store it in a database.
   * @param updatedProducer dto object with data to update the producer object
   * @param id id of the producer object to be updated
   * @return dto of the producer object that was updated or newly created
   */
  @Override
  public ProducerResponse updateOrSaveNew(StoreProducerRequest updatedProducer, Long id) {
    return producerRepository.getProducerById(id)
        .map(prod -> {
          prod.setNewProperties(updatedProducer);
          producerRepository.save(prod);
          return ProducerMapper.toProdResponse(prod);
        })
        .orElseGet(() -> insert(updatedProducer));
  }


  /**
   * Finds a producer object that has specific id and calls the repository to delete the object from the database.
   * @param id the id of the producer to be deleted
   * @return dto of the producer that was deleted
   */
  @Override
  public ProducerResponse deleteById(Long id) {
    Producer producer = getProducerEntity(id);
    producerRepository.delete(producer);
    return ProducerMapper.toProdResponse(producer);
  }

  /**
   * Searches for a producer with specific id.
   * @param id the id of the requested producer object
   * @throws RecordNotFoundException if no producer object of given id was found
   * @return the producer object
   */
  @Override
  public Producer getProducerEntity(Long id) {
    return producerRepository.getProducerById(id).orElseThrow(() -> new RecordNotFoundException(id));
  }
}