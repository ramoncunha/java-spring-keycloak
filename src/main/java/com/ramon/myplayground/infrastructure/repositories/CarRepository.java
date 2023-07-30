package com.ramon.myplayground.infrastructure.repositories;

import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = {"engine"})
    List<CarEntity> findAll();
}
