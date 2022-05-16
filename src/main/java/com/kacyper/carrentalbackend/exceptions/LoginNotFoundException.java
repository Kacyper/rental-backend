package com.kacyper.carrentalbackend.exceptions;

public class LoginNotFoundException extends Exception {

    @Override
    public String getMessage() {
        return "Login not found.";
    }
}
