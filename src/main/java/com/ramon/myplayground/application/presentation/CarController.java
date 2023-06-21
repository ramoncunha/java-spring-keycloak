package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.application.mappers.CarMapper;
import com.ramon.myplayground.application.services.HateoasLinkService;
import com.ramon.myplayground.application.services.ICarService;
import com.ramon.myplayground.domain.models.CarResponse;
import com.ramon.myplayground.domain.models.CarEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CarController {

    private final ICarService carService;
    private final HateoasLinkService hateoasLinkService;

    @PostMapping("/car")
    public ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.save(carRequest);
        CarResponse newCar = CarMapper.fromCarEntity(carEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCar);
    }

    @GetMapping("/car")
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> carResponseList = carService.findAll().stream()
                .map(CarMapper::fromCarEntity)
                .peek(car -> car.add(
                        hateoasLinkService.getOneLink(car.getIdCar()))
                ).collect(Collectors.toList());
        return ResponseEntity.ok(carResponseList);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarResponse> getOneCar(@PathVariable(value = "id") UUID id) {
        CarEntity carEntity = carService.findById(id);
        CarResponse car = CarMapper.fromCarEntity(carEntity);
        car.add(hateoasLinkService.getAllLink());
        car.add(hateoasLinkService.deleteLink(car.getIdCar()));
        return ResponseEntity.ok(car);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.update(id, carRequest);
        return ResponseEntity.ok(CarMapper.fromCarEntity(carEntity));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "id")UUID id) {
        carService.delete(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }
}
