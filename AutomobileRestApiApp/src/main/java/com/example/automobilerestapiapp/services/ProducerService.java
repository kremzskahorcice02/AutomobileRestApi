package com.example.automobilerestapiapp.services;

import com.example.automobilerestapiapp.dtos.ProducerResponse;
import com.example.automobilerestapiapp.dtos.StoreProducerRequest;
import com.example.automobilerestapiapp.exceptions.RecordNotFoundException;
import com.example.automobilerestapiapp.models.Producer;
import java.util.List;

public interface ProducerService {

  /**
   * Fetches all producer objects from repo and converts the objects to dtos.
   * @return arraylist of producer dtos
   */
  List<ProducerResponse> getAll();

  /**
   * Fetches a producer object from repo of given id and converts the object to dto.
   * @param id the id of the desired producer
   * @throws RecordNotFoundException if producer of given id was not found
   * @return dto of the producer with given id
   */
  ProducerResponse getById(Long id);

  /**
   * Creates new producer object from dto and saves it in a database
   * @param newProducer dto object with data to create new producer entity
   * @return dto of the producer that was created
   */
  ProducerResponse insert(StoreProducerRequest newProducer);

  /**
   * Serves for updating producer object stored in database. It fetches model of given id and updates
   * all the fields respectively to the new data object passed as an argument.
   * If model of given id was not found it calls the {@link #insert(StoreProducerRequest)} method to create
   * completely new producer object and to store it in a database.
   * @param updatedProducer dto object with data to update the producer object
   * @param id id of the producer object to be updated
   * @return dto of the producer object that was updated or newly created
   */
  ProducerResponse updateOrSaveNew(StoreProducerRequest updatedProducer, Long id);

  /**
   * Finds a producer object that has specific id and calls the repository to delete the object from the database.
   * @param id the id of the producer to be deleted
   * @return dto of the producer that was deleted
   */
  ProducerResponse deleteById(Long id);

  /**
   * Searches for a producer with specific id.
   * @param id the id of the requested producer object
   * @throws RecordNotFoundException if no producer object of given id was found
   * @return the producer object
   */
  Producer getProducerEntity(Long id);
}
