package com.ramon.myplayground.application.dtos;

import com.ramon.myplayground.domain.models.Fuel;

import java.util.List;

public record EngineRequest(String description,
                            String transmission,
                            Integer horsePower,
                            Double kilometers,
                            List<Fuel> fuel) { }
