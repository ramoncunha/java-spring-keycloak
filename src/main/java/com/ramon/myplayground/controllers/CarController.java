package com.ramon.myplayground.controllers;

import com.ramon.myplayground.dtos.CarRecord;
import com.ramon.myplayground.exceptions.CarNotFoundException;
import com.ramon.myplayground.models.CarModel;
import com.ramon.myplayground.repositories.CarRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class CarController {

    private final CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping("/car")
    public ResponseEntity<CarModel> saveCar(@RequestBody @Valid CarRecord carRecord) {
        var car = new CarModel();
        BeanUtils.copyProperties(carRecord, car);
        return ResponseEntity.status(HttpStatus.CREATED).body(carRepository.save(car));
    }

    @GetMapping("/car")
    public ResponseEntity<List<CarModel>> getAllCars() {
        List<CarModel> carList = carRepository.findAll();

        carList.forEach(car -> {
            UUID idCar = car.getIdCar();
            car.add(linkTo(methodOn(CarController.class)
                    .getOneCar(idCar))
                    .withSelfRel()
            );
        });

        return ResponseEntity.ok(carList);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarModel> getOneCar(@PathVariable(value = "id") UUID id) {
        Optional<CarModel> carOptional = carRepository.findById(id);

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
    public ResponseEntity<CarModel> updateCar(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid CarRecord carRecord) {
        Optional<CarModel> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }

        var car = carOptional.get();
        BeanUtils.copyProperties(carRecord, car);
        return ResponseEntity.ok(carRepository.save(car));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "id")UUID id) {
        Optional<CarModel> carOptional = carRepository.findById(id);

        if (carOptional.isEmpty()) {
            throw new CarNotFoundException();
        }
        carRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }
}
