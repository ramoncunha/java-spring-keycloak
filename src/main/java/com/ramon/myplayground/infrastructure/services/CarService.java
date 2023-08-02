package com.ramon.myplayground.infrastructure.services;

import com.ramon.myplayground.application.presentation.dtos.CarRequest;
import com.ramon.myplayground.infrastructure.mappers.CarEntityMapper;
import com.ramon.myplayground.application.services.ICarService;
import com.ramon.myplayground.domain.exceptions.CarNotFoundException;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {

    private final CarRepository carRepository;
    private final CarEntityMapper carEntityMapper;

    @Override
    public CarEntity save(CarRequest carRequest) {
        CarEntity carToSave = carEntityMapper.map(carRequest);
        return carRepository.save(carToSave);
    }

    @Override
    public List<CarEntity> findAll() {
        return carRepository.findAll();
    }

    @Override
    public CarEntity findById(UUID id) {
        Optional<CarEntity> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        return carOptional.get();
    }

    @Override
    public CarEntity update(UUID id, CarRequest carRequest) {
        Optional<CarEntity> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        CarEntity car = carOptional.get();
        return carRepository.save(car);
    }

    @Override
    public void delete(UUID id) {
        Optional<CarEntity> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(id);
    }
}
