package com.example.intuit.businessprofileservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class NoSubscriptionsFoundExceptionTest {

    @Mock
    private Throwable mockCause;

    private NoSubscriptionsFoundException noSubscriptionsFoundExceptionUnderTest;

    @BeforeEach
    void setUp() {
        noSubscriptionsFoundExceptionUnderTest = new NoSubscriptionsFoundException("message", mockCause);
    }

    @Test
    void testGetMessage() {
        assertThat(noSubscriptionsFoundExceptionUnderTest.getMessage()).isEqualTo("message");
    }

    @Test
    void testGetCause() {
        assertThat(noSubscriptionsFoundExceptionUnderTest.getCause()).isEqualTo(mockCause);
    }
}
