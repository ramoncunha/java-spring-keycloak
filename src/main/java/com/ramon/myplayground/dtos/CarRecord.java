package com.ramon.myplayground.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CarRecord(@NotBlank String brand, @NotBlank String model, @NotNull BigDecimal price) {
}
