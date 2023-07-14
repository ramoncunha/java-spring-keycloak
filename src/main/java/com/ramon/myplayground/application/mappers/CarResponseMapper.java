package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.application.dtos.CarResponse;
import com.ramon.myplayground.domain.models.CarEntity;

public class CarResponseMapper {

    public static CarResponse fromCarEntity(CarEntity carEntity) {
        return new CarResponse(
                carEntity.getId(),
                carEntity.getMake(),
                carEntity.getModel(),
                carEntity.getPrice()
        );
    }
}
