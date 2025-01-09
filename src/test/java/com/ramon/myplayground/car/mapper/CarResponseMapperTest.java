package com.ramon.myplayground.car.mapper;

import com.ramon.myplayground.car.dto.CarResponse;
import com.ramon.myplayground.car.dto.EngineResponse;
import com.ramon.myplayground.user.dto.UserResponse;
import com.ramon.myplayground.car.model.CarEntity;
import com.ramon.myplayground.car.model.EngineEntity;
import com.ramon.myplayground.user.mapper.UserResponseMapper;
import com.ramon.myplayground.user.model.UserEntity;
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
                carEntity.getPrice(),
                carEntity.getYearMake(),
                carEntity.getYearModel(),
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
