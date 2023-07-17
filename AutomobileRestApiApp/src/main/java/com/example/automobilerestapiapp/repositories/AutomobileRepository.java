package com.example.automobilerestapiapp.repositories;

import com.example.automobilerestapiapp.dtos.AutomobileDriveAbilityResponse;
import com.example.automobilerestapiapp.models.Automobile;
import jakarta.persistence.criteria.CriteriaBuilder.In;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomobileRepository extends JpaRepository<Automobile,Long> {
  Optional<Automobile> getAutomobileById(Long id);
  Integer countAutomobilesByIsDriveableTrue();

  Integer countAutomobilesByIsDriveableFalse();
}