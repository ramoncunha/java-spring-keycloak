package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.application.presentation.dtos.CarRequest;
import com.ramon.myplayground.application.presentation.dtos.EngineRequest;
import com.ramon.myplayground.infrastructure.repositories.models.CarEntity;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;
import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Year;

@ExtendWith(MockitoExtension.class)
class CarEntityMapperTest {

    @Mock
    private UserEntityMapper userEntityMapper;
    @Mock
    private EngineEntityMapper engineEntityMapper;
    @InjectMocks
    private CarEntityMapper unit;

    @Test
    void map_givenCarRequest_shouldReturnCarEntity() {
        var engineRequest = Mockito.mock(EngineRequest.class);
        var carRequest = new CarRequest("Volkswagen",
                "Nivus",
                Year.of(2023),
                Year.of(2023),
                BigDecimal.TEN,
                "blue",
                "1234567",
                engineRequest);
        var userEntity = Mockito.mock(UserEntity.class);
        var engineEntity = Mockito.mock(EngineEntity.class);

        Mockito.when(userEntityMapper.map("1234567")).thenReturn(userEntity);
        Mockito.when(engineEntityMapper.map(engineRequest)).thenReturn(engineEntity);

        var expected = CarEntity.builder()
                .make(carRequest.make())
                .model(carRequest.model())
                .yearMake(carRequest.yearMake())
                .yearModel(carRequest.yearModel())
                .price(carRequest.price())
                .color(carRequest.color())
                .user(userEntity)
                .engine(engineEntity)
                .build();

        CarEntity actual = unit.map(carRequest);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
