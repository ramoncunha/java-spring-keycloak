package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.domain.models.CarEntity;
import com.ramon.myplayground.domain.models.UserEntity;

import java.util.UUID;

public class CarEntityMapper {

    public static CarEntity fromCarRequest(CarRequest carRequest) {
        return CarEntity.builder()
                .make(carRequest.brand())
                .model(carRequest.model())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(UserEntity.builder()
                        .id(UUID.fromString(carRequest.userId()))
                        .build())
                .build();
    }
}
