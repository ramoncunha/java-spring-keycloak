package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.dtos.EngineResponse;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;

import java.util.stream.Collectors;

public class EngineResponseMapper {

    public static EngineResponse fromEngineEntity(EngineEntity engine) {
        return new EngineResponse(
                engine.getDescription(),
                engine.getTransmission(),
                engine.getHorsePower(),
                engine.getKilometers(),
                engine.getFuel().stream()
                        .map(Enum::toString)
                        .collect(Collectors.joining(","))
        );
    }
}
