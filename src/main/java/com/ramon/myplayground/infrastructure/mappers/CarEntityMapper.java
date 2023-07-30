package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.application.presentation.dtos.CarRequest;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;

public class CarEntityMapper {

    public static CarEntity map(CarRequest carRequest) {
        return CarEntity.builder()
                .make(carRequest.make())
                .model(carRequest.model())
                .yearMake(carRequest.yearMake())
                .yearModel(carRequest.yearModel())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(UserEntityMapper.map(carRequest.userId()))
                .engine(EngineEntityMapper.map(carRequest.engineRequest()))
                .build();
    }
}
