package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.EngineRequest;
import com.ramon.myplayground.domain.models.EngineEntity;

public class EngineEntityMapper {

    public static EngineEntity fromEngineRequest(EngineRequest engine) {
        return EngineEntity.builder()
                .description(engine.description())
                .transmission(engine.transmission())
                .horsePower(engine.horsePower())
                .kilometers(engine.kilometers())
                .fuel(engine.fuel())
                .build();
    }
}
