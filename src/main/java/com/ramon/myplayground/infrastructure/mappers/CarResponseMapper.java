package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.dtos.CarResponse;
import com.ramon.myplayground.domain.models.CarEntity;
import org.springframework.stereotype.Component;

@Component
public class CarResponseMapper {

    public CarResponse fromCarEntity(CarEntity carEntity) {
        return new CarResponse(
                carEntity.getId(),
                carEntity.getMake(),
                carEntity.getModel(),
                carEntity.getYearMake(),
                carEntity.getYearModel(),
                carEntity.getPrice(),
                carEntity.getColor(),
                EngineResponseMapper.fromEngineEntity(carEntity.getEngine())
        );
    }
}
