package com.ramon.myplayground.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Car extends RepresentationModel<Car>  {

    private final UUID idCar;
    private final String brand;
    private final String model;
    private final BigDecimal price;
}
