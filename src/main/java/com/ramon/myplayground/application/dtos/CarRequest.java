package com.ramon.myplayground.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarRequest(@NotBlank String brand,
                         @NotBlank String model,
                         @NotNull BigDecimal price,
                         @NotBlank String color,
                         @NotBlank String userId,
                         @NotNull EngineRequest engineRequest) {
}
