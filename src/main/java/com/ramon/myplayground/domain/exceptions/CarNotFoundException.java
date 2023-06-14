package com.ramon.myplayground.domain.exceptions;

public class CarNotFoundException extends RuntimeException {

    public CarNotFoundException() {
        super("Car not found!");
    }
}
