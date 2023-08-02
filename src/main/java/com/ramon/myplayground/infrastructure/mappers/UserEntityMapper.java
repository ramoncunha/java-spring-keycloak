package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserEntityMapper {

    public UserEntity map(String id) {
        return UserEntity.builder()
                .id(UUID.fromString(id))
                .build();
    }
}
