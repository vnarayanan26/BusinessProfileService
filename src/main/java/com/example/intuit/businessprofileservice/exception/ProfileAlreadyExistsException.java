package com.example.intuit.businessprofileservice.exception;

public class ProfileAlreadyExistsException extends BusinessProfileException {
    public ProfileAlreadyExistsException(String message) {
        super(message);
    }

    public ProfileAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
