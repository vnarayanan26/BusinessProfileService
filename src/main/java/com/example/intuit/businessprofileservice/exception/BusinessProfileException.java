package com.example.intuit.businessprofileservice.exception;

public class BusinessProfileException extends RuntimeException{
    public BusinessProfileException(String message) {
        super(message);
    }

    public BusinessProfileException(String message, Throwable cause) {
        super(message, cause);
    }
}
