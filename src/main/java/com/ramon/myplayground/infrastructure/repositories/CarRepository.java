package com.ramon.myplayground.infrastructure.repositories;

import com.ramon.myplayground.domain.models.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {
}
