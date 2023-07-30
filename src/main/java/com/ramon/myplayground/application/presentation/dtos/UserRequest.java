package com.ramon.myplayground.application.presentation.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserRequest(@NotBlank String name, @NotBlank String email) {
}
