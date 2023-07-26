package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;

import java.util.UUID;

public class UserEntityMapper {

    public static UserEntity fromRequest(String id) {
        return UserEntity.builder()
                .id(UUID.fromString(id))
                .build();
    }
}
