package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.models.EngineResponse;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class EngineResponseMapper {

    public EngineResponse map(EngineEntity engine) {
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
