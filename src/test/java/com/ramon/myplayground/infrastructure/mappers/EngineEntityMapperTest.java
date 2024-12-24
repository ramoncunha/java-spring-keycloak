package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.domain.models.Fuel;
import com.ramon.myplayground.infrastructure.models.EngineRequest;
import com.ramon.myplayground.infrastructure.repositories.models.EngineEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class EngineEntityMapperTest {

    private final EngineEntityMapper unit = new EngineEntityMapper();

    @Test
    void map_givenEngineRequest_shouldReturnEngineEntity() {
        var engineRequest = new EngineRequest("Turbo",
                "Automatic",
                189,
                20_000D,
                List.of(Fuel.GASOLINE, Fuel.ETHANOL));

        var expected = EngineEntity.builder()
                .description(engineRequest.description())
                .transmission(engineRequest.transmission())
                .horsePower(engineRequest.horsePower())
                .kilometers(engineRequest.kilometers())
                .fuel(engineRequest.fuel())
                .build();

        EngineEntity actual = unit.map(engineRequest);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
