package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.application.presentation.dtos.EngineResponse;
import com.ramon.myplayground.domain.models.Fuel;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EngineResponseMapperTest {

    private final EngineResponseMapper unit = new EngineResponseMapper();

    @Test
    void map_givenEngineEntity_shouldReturnEngineResponse() {
        var engineEntity = EngineEntity.builder()
                .description("AP")
                .transmission("Manual")
                .horsePower(71)
                .kilometers(0D)
                .fuel(List.of(Fuel.DIESEL, Fuel.ETHANOL))
                .build();

        var expected = new EngineResponse(
                engineEntity.getDescription(),
                engineEntity.getTransmission(),
                engineEntity.getHorsePower(),
                engineEntity.getKilometers(),
                "DIESEL,ETHANOL");

        EngineResponse actual = unit.map(engineEntity);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
