package com.example.intuit.businessprofileservice.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class AsyncExceptionHandlerTest {

    private AsyncExceptionHandler asyncExceptionHandlerUnderTest;

    public static void method() {

    }

    @BeforeEach
    void setUp() {
        asyncExceptionHandlerUnderTest = new AsyncExceptionHandler();
    }

    @Test
    void testHandleUncaughtException() throws NoSuchMethodException {
        // Setup
        final Method methodMock = this.getClass().getMethod("method");

        // Run the test
        asyncExceptionHandlerUnderTest.handleUncaughtException(
                new Exception("message"), methodMock, "params");

        // Verify the results
    }
}
