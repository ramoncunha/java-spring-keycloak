package com.ramon.myplayground.domain.models;

import java.math.BigDecimal;
import java.util.UUID;

public record Car(UUID idCar, String brand, String model, BigDecimal price) {
}
