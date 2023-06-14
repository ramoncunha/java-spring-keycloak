package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.application.mappers.CarMapper;
import com.ramon.myplayground.application.services.CarService;
import com.ramon.myplayground.domain.exceptions.CarNotFoundException;
import com.ramon.myplayground.domain.models.Car;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.infrastructure.repositories.CarRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarRepository carRepository;
    private final CarService carService;

    @PostMapping("/car")
    public ResponseEntity<Car> saveCar(@RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.save(carRequest);
        Car newCar = CarMapper.fromCarEntity(carEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCar);
    }

    @GetMapping("/car")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> carList = carService.findAll().stream()
                .map(CarMapper::fromCarEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(carList);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarEntity> getOneCar(@PathVariable(value = "id") UUID id) {
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }

        var car = carOptional.get();
        car.add(linkTo(
                methodOn(CarController.class)
                        .getAllCars())
                .withRel("Car List"));
        car.add(linkTo(
                methodOn(CarController.class)
                        .deleteCar(car.getIdCar()))
                .withRel("Delete")
        );
        return ResponseEntity.ok(car);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<CarEntity> updateCar(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid CarRequest carRequest) {
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }

        var car = carOptional.get();
        BeanUtils.copyProperties(carRequest, car);
        return ResponseEntity.ok(carRepository.save(car));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "id")UUID id) {
        Optional<CarEntity> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }
}
