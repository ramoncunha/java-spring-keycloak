package com.ramon.myplayground.application.presentation.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(UUID id, String name, String email) {
}
