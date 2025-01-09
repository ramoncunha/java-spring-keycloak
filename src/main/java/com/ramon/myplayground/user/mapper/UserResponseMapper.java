package com.ramon.myplayground.user.mapper;

import com.ramon.myplayground.user.dto.UserResponse;
import com.ramon.myplayground.user.model.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserResponseMapper {

    public UserResponse map(UserEntity user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
