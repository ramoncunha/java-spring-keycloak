package com.ramon.myplayground.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String name, @NotBlank String email) {
}
