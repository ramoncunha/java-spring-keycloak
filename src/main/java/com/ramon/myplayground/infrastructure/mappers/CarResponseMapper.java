package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.models.CarResponse;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarResponseMapper {

    private final EngineResponseMapper engineMapper;
    private final UserResponseMapper userMapper;

    public CarResponse map(CarEntity carEntity) {
        return new CarResponse(
                carEntity.getId(),
                carEntity.getMake(),
                carEntity.getModel(),
                carEntity.getYearMake(),
                carEntity.getYearModel(),
                carEntity.getPrice(),
                carEntity.getColor(),
                engineMapper.map(carEntity.getEngine()),
                userMapper.map(carEntity.getUser())
        );
    }
}
