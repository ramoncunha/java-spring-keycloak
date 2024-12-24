package com.ramon.myplayground.infrastructure.models;

public record EngineResponse(String description,
                             String transmission,
                             Integer horsePower,
                             Double kilometers,
                             String fuel) {
}
