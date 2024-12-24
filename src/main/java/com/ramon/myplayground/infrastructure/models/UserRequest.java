package com.ramon.myplayground.infrastructure.models;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String name, @NotBlank String email) {
}
