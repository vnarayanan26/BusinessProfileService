package com.example.intuit.businessprofileservice.exception;

public class ValidationNotFoundException extends BusinessProfileException {

    public ValidationNotFoundException(String message) {
        super(message);
    }

    public ValidationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
