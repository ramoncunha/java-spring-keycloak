package com.ramon.myplayground.application.presentation;

import com.ramon.myplayground.infrastructure.dtos.CarRequest;
import com.ramon.myplayground.infrastructure.mappers.CarResponseMapper;
import com.ramon.myplayground.application.services.HateoasLinkService;
import com.ramon.myplayground.application.services.ICarService;
import com.ramon.myplayground.infrastructure.dtos.CarResponse;
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
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {

    private final ICarService carService;
    private final HateoasLinkService hateoasLinkService;

    @PostMapping
    public ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.save(carRequest);
        CarResponse newCar = CarResponseMapper.fromCarEntity(carEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCar);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> carResponseList = carService.findAll().stream()
                .map(CarResponseMapper::fromCarEntity)
                .peek(car -> car.add(
                        hateoasLinkService.getOneLink(car.getIdCar()))
                ).collect(Collectors.toList());
        return ResponseEntity.ok(carResponseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getOneCar(@PathVariable(value = "id") UUID id) {
        CarEntity carEntity = carService.findById(id);
        CarResponse car = CarResponseMapper.fromCarEntity(carEntity);
        car.add(hateoasLinkService.getAllLink());
        car.add(hateoasLinkService.deleteLink(car.getIdCar()));
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.update(id, carRequest);
        return ResponseEntity.ok(CarResponseMapper.fromCarEntity(carEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCar(@PathVariable(value = "id")UUID id) {
        carService.delete(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }
}
