package com.example.automobilerestapiapp.repositories;

import com.example.automobilerestapiapp.models.Producer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Long> {
  boolean existsById(Long id);

  Optional<Producer> getProducerById(Long id);
  Producer deleteProducerById(Long id);
}
