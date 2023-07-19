package com.ramon.myplayground.application.dtos;

public record EngineResponse(String description,
                             String transmission,
                             Integer horsePower,
                             Double kilometers,
                             String fuel) { }
