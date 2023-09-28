package com.example.intuit.businessprofileservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProfileValidationExceptionTest {

    @Mock
    private Throwable mockCause;

    private ProfileValidationException profileValidationExceptionUnderTest;

    @BeforeEach
    void setUp() {
        profileValidationExceptionUnderTest = new ProfileValidationException("message", mockCause);
    }

    @Test
    void testGetMessage() {
        assertThat(profileValidationExceptionUnderTest.getMessage()).isEqualTo("message");
    }

    @Test
    void testGetCause() {
        assertThat(profileValidationExceptionUnderTest.getCause()).isEqualTo(mockCause);
    }
}
