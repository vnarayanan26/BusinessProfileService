package com.example.intuit.businessprofileservice.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionIdTest {

    @Test
    void testGetId() {
        final SubscriptionId subscriptionIdUnderTest = new SubscriptionId();
        subscriptionIdUnderTest.setId("id");
        assertThat(subscriptionIdUnderTest.getId()).isEqualTo("id");
    }

    @Test
    void testSetId() {
        // Setup
        final SubscriptionId subscriptionIdUnderTest = new SubscriptionId();

        // Run the test
        subscriptionIdUnderTest.setId("id");

        // Verify the results
    }

    @Test
    void testCustomerIDGetterAndSetter() {
        final SubscriptionId subscriptionIdUnderTest = new SubscriptionId();
        final String customerID = "customerID";
        subscriptionIdUnderTest.setCustomerID(customerID);
        assertThat(subscriptionIdUnderTest.getCustomerID()).isEqualTo(customerID);
    }
}
