package com.example.intuit.businessprofileservice.exception;

public class ProfileValidationException extends BusinessProfileException {
    public ProfileValidationException(String message) {
        super(message);
    }

    public ProfileValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
