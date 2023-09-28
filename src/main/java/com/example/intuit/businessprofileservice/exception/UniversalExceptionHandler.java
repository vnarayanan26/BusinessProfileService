package com.example.intuit.businessprofileservice.exception;

import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@Slf4j
public class UniversalExceptionHandler {
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<String> entityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return ResponseEntity.badRequest().body(Constants.PROFILE_NOT_FOUND_MESSAGE);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> serviceUnavailableException(Exception serviceNotAvailableException) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(serviceNotAvailableException.getMessage());
    }

    @ExceptionHandler(value = ProfileValidationException.class)
    public ResponseEntity<String> profileValidationException(ProfileValidationException profileValidationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(profileValidationException.getMessage());
    }

    @ExceptionHandler(value = ProfileNotFoundException.class)
    public ResponseEntity<String> profileNotFoundException(ProfileNotFoundException profileNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(profileNotFoundException.getMessage());
    }

    @ExceptionHandler(value = SubscriptionNotFoundException.class)
    public ResponseEntity<String> subscriptionNotFoundException(SubscriptionNotFoundException subscriptionNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(subscriptionNotFoundException.getMessage());
    }

    @ExceptionHandler(value = ValidationNotFoundException.class)
    public ResponseEntity<String> validationNotFoundException(ValidationNotFoundException validationNotFoundException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationNotFoundException.getMessage());
    }

    @ExceptionHandler(value = NoSubscriptionsFoundException.class)
    public void noSubscriptionsFoundException(NoSubscriptionsFoundException noSubscriptionsFoundException) {
        log.info(noSubscriptionsFoundException.getMessage());
    }
}
