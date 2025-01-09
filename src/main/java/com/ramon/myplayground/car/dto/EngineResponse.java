package com.ramon.myplayground.car.dto;

public record EngineResponse(String description,
                             String transmission,
                             Integer horsePower,
                             Double kilometers,
                             String fuel) {
}
