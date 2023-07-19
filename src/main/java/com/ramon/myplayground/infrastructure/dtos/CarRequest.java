package com.ramon.myplayground.infrastructure.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Year;

public record CarRequest(@NotBlank String make,
                         @NotBlank String model,
                         @NotNull Year yearMake,
                         @NotNull Year yearModel,
                         @NotNull BigDecimal price,
                         @NotBlank String color,
                         @NotBlank String userId,
                         @NotNull EngineRequest engineRequest) {
}
