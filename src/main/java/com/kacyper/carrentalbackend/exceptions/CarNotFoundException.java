package com.kacyper.carrentalbackend.exceptions;

public class CarNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Car not found.";
    }
}
