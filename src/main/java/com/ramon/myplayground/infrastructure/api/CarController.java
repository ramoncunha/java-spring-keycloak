package com.ramon.myplayground.infrastructure.api;

import com.ramon.myplayground.domain.ICarService;
import com.ramon.myplayground.infrastructure.HateoasLinkService;
import com.ramon.myplayground.infrastructure.mappers.CarResponseMapper;
import com.ramon.myplayground.infrastructure.models.CarRequest;
import com.ramon.myplayground.infrastructure.models.CarResponse;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final ICarService carService;
    private final HateoasLinkService hateoasLinkService;
    private final CarResponseMapper carResponseMapper;

    @PostMapping
    public ResponseEntity<CarResponse> saveCar(@RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.save(carRequest);
        CarResponse newCar = carResponseMapper.map(carEntity);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newCar);
    }

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<CarResponse> carResponse = carService.findAll().stream()
                .map(carResponseMapper::map)
                .toList();
        carResponse.forEach(car -> car.add(hateoasLinkService.getOneLink(car.getId())));
        return ResponseEntity.ok(carResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getOneCar(@PathVariable(value = "id") UUID id) {
        CarEntity carEntity = carService.findById(id);
        CarResponse car = carResponseMapper.map(carEntity);
        car.add(hateoasLinkService.getAllLink());
        car.add(hateoasLinkService.deleteLink(car.getId()));
        return ResponseEntity.ok(car);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Valid CarRequest carRequest) {
        CarEntity carEntity = carService.update(id, carRequest);
        return ResponseEntity.ok(carResponseMapper.map(carEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable(value = "id") UUID id) {
        carService.delete(id);
        return ResponseEntity.ok().build();
    }
}
