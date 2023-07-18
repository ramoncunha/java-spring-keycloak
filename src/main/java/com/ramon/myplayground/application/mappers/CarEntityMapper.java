package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.application.dtos.EngineRequest;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.domain.models.EngineEntity;
import com.ramon.myplayground.domain.models.UserEntity;

import java.util.UUID;

public class CarEntityMapper {

    public static CarEntity fromCarRequest(CarRequest carRequest) {
        EngineRequest engine = carRequest.engineRequest();
        return CarEntity.builder()
                .make(carRequest.make())
                .model(carRequest.model())
                .yearMake(carRequest.yearMake())
                .yearModel(carRequest.yearModel())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(UserEntity.builder()
                        .id(UUID.fromString(carRequest.userId()))
                        .build())
                .engine(EngineEntity.builder()
                        .description(engine.description())
                        .transmission(engine.transmission())
                        .horsePower(engine.horsePower())
                        .kilometers(engine.kilometers())
                        .build())
                .build();
    }
}
