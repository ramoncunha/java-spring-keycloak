package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.CarRequest;
import com.ramon.myplayground.domain.models.CarEntity;

public class CarEntityMapper {

    public static CarEntity fromCarRequest(CarRequest carRequest) {
        return CarEntity.builder()
                .brand(carRequest.brand())
                .model(carRequest.model())
                .price(carRequest.price())
                .build();
    }
}
