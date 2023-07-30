package com.ramon.myplayground.application.presentation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.Year;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CarResponse extends RepresentationModel<CarResponse>  {

    private final UUID id;
    private final String make;
    private final String model;
    private Year yearMake;
    private Year yearModel;
    private final BigDecimal price;
    private String color;
    private EngineResponse engine;
    private UserResponse user;
}
