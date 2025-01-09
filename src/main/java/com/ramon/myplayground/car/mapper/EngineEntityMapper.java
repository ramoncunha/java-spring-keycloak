package com.ramon.myplayground.car.mapper;

import com.ramon.myplayground.car.dto.EngineRequest;
import com.ramon.myplayground.car.model.EngineEntity;
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
