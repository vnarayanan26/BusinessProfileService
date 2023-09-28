package com.example.intuit.businessprofileservice.exception;

public class NoSubscriptionsFoundException extends BusinessProfileException{

    public NoSubscriptionsFoundException(String message) {
        super(message);
    }

    public NoSubscriptionsFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
