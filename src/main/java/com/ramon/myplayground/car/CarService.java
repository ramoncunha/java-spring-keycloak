package com.ramon.myplayground.car;

import com.ramon.myplayground.car.dto.CarRequest;
import com.ramon.myplayground.car.exceptions.CarNotFoundException;
import com.ramon.myplayground.car.mapper.CarEntityMapper;
import com.ramon.myplayground.car.model.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarEntityMapper carEntityMapper;

    public CarEntity save(CarRequest carRequest) {
        CarEntity carToSave = carEntityMapper.map(carRequest);
        return carRepository.save(carToSave);
    }

    public List<CarEntity> findAll() {
        return carRepository.findAll();
    }

    public CarEntity findById(UUID id) {
        return carRepository.findById(id).orElseThrow(CarNotFoundException::new);
    }

    public CarEntity update(UUID id, CarRequest carRequest) {
        if (!carRepository.existsById(id)) {
            throw new CarNotFoundException();
        }
        CarEntity carToUpdate = carEntityMapper.map(carRequest);
        return carRepository.save(carToUpdate);
    }

    public void delete(UUID id) {
        carRepository.deleteById(id);
    }
}
