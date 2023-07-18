package com.ramon.myplayground.application.dtos;

import com.ramon.myplayground.domain.models.Fuel;

public record EngineRequest(String description,
                            String transmission,
                            Integer horsePower,
                            Double kilometers,
                            Fuel fuel) {
}
