package com.ramon.myplayground.infrastructure.services;

import com.ramon.myplayground.domain.ICarService;
import com.ramon.myplayground.domain.exceptions.CarNotFoundException;
import com.ramon.myplayground.infrastructure.mappers.CarEntityMapper;
import com.ramon.myplayground.infrastructure.models.CarRequest;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    @Override
    public CarEntity update(UUID id, CarRequest carRequest) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException();
        }
        CarEntity carToUpdate = carEntityMapper.map(carRequest);
        return carRepository.save(carToUpdate);
    }

    @Override
    public void delete(UUID id) {
        carRepository.deleteById(id);
    }
}
