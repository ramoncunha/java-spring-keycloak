package com.ramon.myplayground.car.dto;

import java.util.List;

public record EngineRequest(String description,
                            String transmission,
                            Integer horsePower,
                            Double kilometers,
                            List<Fuel> fuel) {
}
