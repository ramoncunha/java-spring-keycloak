package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.domain.models.CarResponse;
import com.ramon.myplayground.domain.models.CarEntity;

public class CarMapper {

    public static CarResponse fromCarEntity(CarEntity carEntity) {
        return new CarResponse(
                carEntity.getIdCar(),
                carEntity.getBrand(),
                carEntity.getModel(),
                carEntity.getPrice()
        );
    }
}
