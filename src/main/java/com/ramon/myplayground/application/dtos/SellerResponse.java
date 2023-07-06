package com.ramon.myplayground.application.dtos;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SellerResponse(UUID id, String name, String email) {
}
