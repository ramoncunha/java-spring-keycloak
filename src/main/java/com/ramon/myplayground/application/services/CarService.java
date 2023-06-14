package com.ramon.myplayground.application.services;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.application.mappers.CarEntityMapper;
import com.ramon.myplayground.domain.exceptions.CarNotFoundException;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import com.ramon.myplayground.infrastructure.services.ICarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService implements ICarService {

    private final CarRepository carRepository;
    private final HateoasLinkService hateoasLinkService;

    @Override
    public CarEntity save(CarRequest carRequest) {
        CarEntity carToSave = CarEntityMapper.fromCarRequest(carRequest);
        return carRepository.save(carToSave);
    }

    @Override
    public List<CarEntity> findAll() {
        List<CarEntity> carList = carRepository.findAll();
        carList.forEach(hateoasLinkService::getOneLink);
        return carList;
    }

    @Override
    public CarEntity findById(UUID id) {
        Optional<CarEntity> carOptional = carRepository.findById(id);
        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        CarEntity car = carOptional.get();
        hateoasLinkService.deleteLink(car);
        hateoasLinkService.getAllLink(car);
        return null;
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
