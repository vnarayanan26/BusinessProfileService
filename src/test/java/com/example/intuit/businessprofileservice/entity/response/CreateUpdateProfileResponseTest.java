package com.example.intuit.businessprofileservice.entity.response;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUpdateProfileResponseTest {

    private CreateUpdateProfileResponse createUpdateProfileResponseUnderTest;

    @BeforeEach
    void setUp() {
        createUpdateProfileResponseUnderTest = new CreateUpdateProfileResponse();
    }

    @Test
    void testCustomerIdGetterAndSetter() {
        final String customerId = "customerId";
        createUpdateProfileResponseUnderTest.setCustomerId(customerId);
        assertThat(createUpdateProfileResponseUnderTest.getCustomerId()).isEqualTo(customerId);
    }

    @Test
    void testValidationRequestIdGetterAndSetter() {
        final String validationRequestId = "validationRequestId";
        createUpdateProfileResponseUnderTest.setValidationRequestId(validationRequestId);
        assertThat(createUpdateProfileResponseUnderTest.getValidationRequestId())
                .isEqualTo(validationRequestId);
    }

    @Test
    void testEquals() {
        assertThat(createUpdateProfileResponseUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        assertThat(createUpdateProfileResponseUnderTest.canEqual("other")).isFalse();
    }


    @Test
    void testToString() {
        assertThat(createUpdateProfileResponseUnderTest.toString()).isEqualTo("CreateUpdateProfileResponse(customerId=null, validationRequestId=null)");
    }
}
