package com.ramon.myplayground.car.mapper;

import com.ramon.myplayground.car.dto.CarResponse;
import com.ramon.myplayground.car.model.CarEntity;
import com.ramon.myplayground.user.mapper.UserResponseMapper;
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
                carEntity.getPrice(),
                carEntity.getYearMake(),
                carEntity.getYearModel(),
                carEntity.getColor(),
                engineMapper.map(carEntity.getEngine()),
                userMapper.map(carEntity.getUser())
        );
    }
}
