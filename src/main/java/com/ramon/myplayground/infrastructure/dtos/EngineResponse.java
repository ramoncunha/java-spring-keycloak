package com.ramon.myplayground.infrastructure.dtos;

public record EngineResponse(String description,
                             String transmission,
                             Integer horsePower,
                             Double kilometers,
                             String fuel) { }
