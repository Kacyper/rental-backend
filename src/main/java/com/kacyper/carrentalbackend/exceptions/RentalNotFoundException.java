package com.kacyper.carrentalbackend.exceptions;

public class RentalNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Rental not found.";
    }
}