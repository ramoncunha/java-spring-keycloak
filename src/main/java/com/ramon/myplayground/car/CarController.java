package com.ramon.myplayground.car;

import com.ramon.myplayground.car.dto.CarRequest;
import com.ramon.myplayground.car.dto.CarResponse;
import com.ramon.myplayground.car.model.CarEntity;
import com.ramon.myplayground.configuration.HateoasLinkService;
import com.ramon.myplayground.car.mapper.CarResponseMapper;
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

    private final CarService carService;
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
