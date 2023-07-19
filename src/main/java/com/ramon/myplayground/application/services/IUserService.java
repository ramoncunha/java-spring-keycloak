package com.ramon.myplayground.application.services;

import com.ramon.myplayground.infrastructure.dtos.UserRequest;
import com.ramon.myplayground.domain.models.UserEntity;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    UserEntity save(UserRequest userRequest);

    List<UserEntity> findAll();

    UserEntity findById(UUID id);

    UserEntity update(UUID id, UserRequest userRequest);

    void delete(UUID id);

}
