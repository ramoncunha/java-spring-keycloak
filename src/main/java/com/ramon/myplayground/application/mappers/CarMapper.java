package com.ramon.myplayground.application.mappers;

import com.ramon.myplayground.domain.models.Car;
import com.ramon.myplayground.domain.models.CarEntity;

public class CarMapper {

    public static Car fromCarEntity(CarEntity carEntity) {
        return new Car(
                carEntity.getIdCar(),
                carEntity.getBrand(),
                carEntity.getModel(),
                carEntity.getPrice()
        );
    }
}
