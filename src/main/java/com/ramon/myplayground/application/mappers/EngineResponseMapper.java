package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.EngineResponse;
import com.ramon.myplayground.domain.models.EngineEntity;

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
