package com.ramon.myplayground.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CarResponse extends RepresentationModel<CarResponse>  {

    private final UUID idCar;
    private final String brand;
    private final String model;
    private final BigDecimal price;
}
