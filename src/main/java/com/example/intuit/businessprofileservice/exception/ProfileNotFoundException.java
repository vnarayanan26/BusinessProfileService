package com.example.intuit.businessprofileservice.exception;

public class ProfileNotFoundException extends BusinessProfileException {

    public ProfileNotFoundException(String message) {
        super(message);
    }

    public ProfileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
