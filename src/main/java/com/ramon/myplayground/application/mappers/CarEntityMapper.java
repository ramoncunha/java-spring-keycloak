package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.domain.models.CarEntity;

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
