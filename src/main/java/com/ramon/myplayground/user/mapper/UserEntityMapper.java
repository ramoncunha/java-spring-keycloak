package com.ramon.myplayground.user.mapper;

import com.ramon.myplayground.user.model.UserEntity;
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
