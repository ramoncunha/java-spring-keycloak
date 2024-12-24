package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.models.CarResponse;
import com.ramon.myplayground.infrastructure.models.EngineResponse;
import com.ramon.myplayground.infrastructure.models.UserResponse;
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
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class CarResponseMapperTest {

    @Mock
    private EngineResponseMapper engineMapper;
    @Mock
    private UserResponseMapper userMapper;
    @InjectMocks
    private CarResponseMapper unit;

    @Test
    void map_givenCarEntity_shouldReturnCarEntity() {
        var engineEntity = Mockito.mock(EngineEntity.class);
        var userEntity = Mockito.mock(UserEntity.class);
        var carEntity = CarEntity.builder()
                .id(UUID.fromString("26a78a2a-30da-11ee-be56-0242ac120002"))
                .make("anyMake")
                .model("anyModel")
                .yearMake(Year.of(2023))
                .yearModel(Year.of(2023))
                .price(BigDecimal.TEN)
                .color("Grey")
                .engine(engineEntity)
                .user(userEntity)
                .build();
        var engineResponse = Mockito.mock(EngineResponse.class);
        var userResponse = Mockito.mock(UserResponse.class);

        Mockito.when(engineMapper.map(engineEntity)).thenReturn(engineResponse);
        Mockito.when(userMapper.map(userEntity)).thenReturn(userResponse);

        var expected = new CarResponse(
                carEntity.getId(),
                carEntity.getMake(),
                carEntity.getModel(),
                carEntity.getYearMake(),
                carEntity.getYearModel(),
                carEntity.getPrice(),
                carEntity.getColor(),
                engineResponse,
                userResponse
        );

        CarResponse actual = unit.map(carEntity);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
