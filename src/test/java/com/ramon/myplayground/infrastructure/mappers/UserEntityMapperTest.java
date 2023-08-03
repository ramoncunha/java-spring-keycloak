package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UserEntityMapperTest {

    private final UserEntityMapper unit = new UserEntityMapper();

    @Test
    void map_givenId_shouldReturnUserEntity() {
        var expected = UserEntity.builder()
                .id(UUID.fromString("26a78a2a-30da-11ee-be56-0242ac120002"))
                .build();
        UserEntity actual = unit.map("26a78a2a-30da-11ee-be56-0242ac120002");

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
