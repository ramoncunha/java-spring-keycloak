package com.ramon.myplayground.infrastructure.repositories;

import com.ramon.myplayground.infrastructure.repositories.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
