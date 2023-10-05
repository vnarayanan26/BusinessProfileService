package com.example.intuit.businessprofileservice.exception;

public class RevisionValidationInProgressException extends BusinessProfileException {
    public RevisionValidationInProgressException(String message) {
        super(message);
    }

    public RevisionValidationInProgressException(String message, Throwable cause) {
        super(message, cause);
    }
}
