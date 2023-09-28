package com.example.intuit.businessprofileservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidProfileRequestException extends BusinessProfileException {
    public InvalidProfileRequestException(String message) {
        super(message);
    }

    public InvalidProfileRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
