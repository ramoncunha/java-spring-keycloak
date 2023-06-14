package com.ramon.myplayground.application.services;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.application.mappers.CarEntityMapper;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import com.ramon.myplayground.infrastructure.services.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {

    private final CarRepository carRepository;
    private final HateoasLink hateoasLink;

    @Override
    public CarEntity save(CarRequest carRequest) {
        CarEntity carToSave = CarEntityMapper.fromCarRequest(carRequest);
        return carRepository.save(carToSave);
    }

    @Override
    public List<CarEntity> findAll() {
        List<CarEntity> carList = carRepository.findAll();
        carList.forEach(hateoasLink::findAllLink);
        return carList;
    }

    @Override
    public CarEntity findById(UUID id) {
        return null;
    }

    @Override
    public CarEntity update(CarRequest carRequest) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
