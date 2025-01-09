package com.ramon.myplayground.car;

import com.ramon.myplayground.car.model.CarEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {

    @Override
    @EntityGraph(attributePaths = {"user", "engine"})
    List<CarEntity> findAll();
}
