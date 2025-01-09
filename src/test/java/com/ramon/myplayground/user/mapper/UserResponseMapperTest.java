package com.ramon.myplayground.user.mapper;

import com.ramon.myplayground.user.dto.UserResponse;
import com.ramon.myplayground.user.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class UserResponseMapperTest {

    private final UserResponseMapper unit = new UserResponseMapper();

    @Test
    void map_givenUserEntity_shouldReturnUserResponse() {
        var user = UserEntity.builder()
                .id(UUID.fromString("26a78a2a-30da-11ee-be56-0242ac120002"))
                .name("John Doe")
                .email("john@app.com")
                .build();

        var expected = new UserResponse(user.getId(), user.getName(), user.getEmail());

        UserResponse actual = unit.map(user);

        Assertions.assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
