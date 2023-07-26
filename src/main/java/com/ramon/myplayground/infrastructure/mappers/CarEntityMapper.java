package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.dtos.CarRequest;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;

public class CarEntityMapper {

    public static CarEntity fromCarRequest(CarRequest carRequest) {
        return CarEntity.builder()
                .make(carRequest.make())
                .model(carRequest.model())
                .yearMake(carRequest.yearMake())
                .yearModel(carRequest.yearModel())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(UserEntityMapper.fromRequest(carRequest.userId()))
                .engine(EngineEntityMapper.fromEngineRequest(carRequest.engineRequest()))
                .build();
    }
}
