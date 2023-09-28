package com.example.intuit.businessprofileservice.exception;

import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class UniversalExceptionHandlerTest {

    private UniversalExceptionHandler universalExceptionHandlerUnderTest;

    @BeforeEach
    void setUp() {
        universalExceptionHandlerUnderTest = new UniversalExceptionHandler();
    }

    @Test
    void testEntityNotFoundException() {
        // Setup
        final EntityNotFoundException entityNotFoundException =
                new EntityNotFoundException("message");
        final ResponseEntity<String> expectedResult = new ResponseEntity<>(Constants.PROFILE_NOT_FOUND_MESSAGE, HttpStatus.BAD_REQUEST);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.entityNotFoundException(entityNotFoundException);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testServiceUnavailableException() {
        // Setup
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("message", HttpStatus.SERVICE_UNAVAILABLE);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.serviceUnavailableException(
                        new Exception("message"));

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testProfileValidationException() {
        // Setup
        final ProfileValidationException profileValidationException =
                new ProfileValidationException("message", new Exception("message"));
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("message", HttpStatus.FORBIDDEN);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.profileValidationException(
                        profileValidationException);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testProfileNotFoundException() {
        // Setup
        final ProfileNotFoundException profileNotFoundException =
                new ProfileNotFoundException("message", new Exception("message"));
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("message", HttpStatus.BAD_REQUEST);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.profileNotFoundException(
                        profileNotFoundException);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSubscriptionNotFoundException() {
        // Setup
        final SubscriptionNotFoundException subscriptionNotFoundException =
                new SubscriptionNotFoundException("message", new Exception("message"));
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("message", HttpStatus.BAD_REQUEST);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.subscriptionNotFoundException(
                        subscriptionNotFoundException);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testValidationNotFoundException() {
        // Setup
        final ValidationNotFoundException validationNotFoundException =
                new ValidationNotFoundException("message", new Exception("message"));
        final ResponseEntity<String> expectedResult = new ResponseEntity<>("message", HttpStatus.BAD_REQUEST);

        // Run the test
        final ResponseEntity<String> result =
                universalExceptionHandlerUnderTest.validationNotFoundException(
                        validationNotFoundException);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testNoSubscriptionsFoundException() {
        // Setup
        final NoSubscriptionsFoundException noSubscriptionsFoundException =
                new NoSubscriptionsFoundException("message", new Exception("message"));

        // Run the test
        universalExceptionHandlerUnderTest.noSubscriptionsFoundException(
                noSubscriptionsFoundException);

        // Verify the results
    }
}
