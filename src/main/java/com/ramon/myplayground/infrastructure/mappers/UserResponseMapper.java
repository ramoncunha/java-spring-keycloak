package com.ramon.myplayground.infrastructure.mappers;

import com.ramon.myplayground.infrastructure.models.UserResponse;
import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapper {

    public UserResponse map(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
