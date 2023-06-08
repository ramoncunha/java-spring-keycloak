package com.ramon.myplayground.repositories;

import com.ramon.myplayground.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarModel, UUID> {
}
