package com.ramon.myplayground.infrastructure.models;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(UUID id, String name, String email) {
}
