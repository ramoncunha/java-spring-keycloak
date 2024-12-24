package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.models.CarRequest;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarEntityMapper {

    private final UserEntityMapper userEntityMapper;
    private final EngineEntityMapper engineEntityMapper;

    public CarEntity map(CarRequest carRequest) {
        return CarEntity.builder()
                .make(carRequest.make())
                .model(carRequest.model())
                .yearMake(carRequest.yearMake())
                .yearModel(carRequest.yearModel())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(userEntityMapper.map(carRequest.userId()))
                .engine(engineEntityMapper.map(carRequest.engineRequest()))
                .build();
    }
}
