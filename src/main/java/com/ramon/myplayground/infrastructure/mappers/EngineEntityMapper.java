package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.application.presentation.dtos.EngineRequest;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;
import org.springframework.stereotype.Component;

@Component
public class EngineEntityMapper {

    public EngineEntity map(EngineRequest engine) {
        return EngineEntity.builder()
                .description(engine.description())
                .transmission(engine.transmission())
                .horsePower(engine.horsePower())
                .kilometers(engine.kilometers())
                .fuel(engine.fuel())
                .build();
    }
}
