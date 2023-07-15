package com.example.automobilerestapiapp.repositories;

import com.example.automobilerestapiapp.models.Model;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModelRepository extends JpaRepository<Model,Long> {
  boolean existsById(Long id);
  Optional<Model> getModelById(Long id);
  Model deleteModelById(Long id);
  Model removeModelById(Long id);
}