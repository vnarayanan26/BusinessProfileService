package com.example.intuit.businessprofileservice.exception;

import com.example.intuit.businessprofileservice.exception.BusinessProfileException;

public class SubscriptionNotFoundException extends BusinessProfileException {

    public SubscriptionNotFoundException(String message) {
        super(message);
    }

    public SubscriptionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
