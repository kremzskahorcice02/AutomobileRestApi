package com.example.automobilerestapiapp.repositories;

import com.example.automobilerestapiapp.models.Automobile;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile,Long> {
  Optional<Automobile> getAutomobileById(Long id);
  Integer countAutomobilesByIsDriveableTrue();

  Integer countAutomobilesByIsDriveableFalse();
}