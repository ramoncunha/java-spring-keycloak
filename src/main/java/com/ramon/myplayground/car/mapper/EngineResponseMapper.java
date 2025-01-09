package com.ramon.myplayground.car.mapper;

import com.ramon.myplayground.car.dto.EngineResponse;
import com.ramon.myplayground.car.model.EngineEntity;
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
