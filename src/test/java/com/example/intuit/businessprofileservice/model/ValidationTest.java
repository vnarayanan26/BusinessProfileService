package com.example.intuit.businessprofileservice.model;

import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ValidationTest {

    @Test
    void testCustomerIDGetterAndSetter() {
        final Validation validationUnderTest = new Validation();
        final String customerID = "customerID";
        validationUnderTest.setCustomerID(customerID);
        assertThat(validationUnderTest.getCustomerID()).isEqualTo(customerID);
    }

    @Test
    void testOverallValidationStatusGetterAndSetter() {
        final Validation validationUnderTest = new Validation();
        final Constants.ValidationStatus overallValidationStatus = Constants.ValidationStatus.In_Progress;
        validationUnderTest.setOverallValidationStatus(overallValidationStatus);
        assertThat(validationUnderTest.getOverallValidationStatus()).isEqualTo(overallValidationStatus);
    }

    @Test
    void testRequestIDGetterAndSetter() {
        final Validation validationUnderTest = new Validation();
        final String requestID = "requestID";
        validationUnderTest.setRequestID(requestID);
        assertThat(validationUnderTest.getRequestID()).isEqualTo(requestID);
    }

    @Test
    void testTimestampGetterAndSetter() {
        final Validation validationUnderTest = new Validation();
        final long timestamp = 0L;
        validationUnderTest.setTimestamp(timestamp);
        assertThat(validationUnderTest.getTimestamp()).isEqualTo(timestamp);
    }
}
