package com.example.intuit.businessprofileservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionTest {

    @Mock
    private SubscriptionId mockSubscriptionId;

    private Subscription subscriptionUnderTest;

    @BeforeEach
    void setUp() {

        mockSubscriptionId =  new SubscriptionId();
        mockSubscriptionId.setId("id");
        mockSubscriptionId.setCustomerID("customerId");
        subscriptionUnderTest = new Subscription();
        subscriptionUnderTest.setId(mockSubscriptionId.getId());
        subscriptionUnderTest.setCustomerID(mockSubscriptionId.getCustomerID());
    }


    @Test
    void testGetId() {
        // Setup
//        when(mockSubscriptionId.getId()).thenReturn("result");

        // Run the test
        final String result = subscriptionUnderTest.getId();

        // Verify the results
        assertThat(result).isEqualTo("id");
    }

    @Test
    void testSetId() {
        // Setup
        // Run the test
        subscriptionUnderTest.setId("id");

        // Verify the results
//        verify(mockSubscriptionId).setId("id");
    }

    @Test
    void testGetCustomerID() {
        // Setup
//        when(mockSubscriptionId.getCustomerID()).thenReturn("result");

        // Run the test
        final String result = subscriptionUnderTest.getCustomerID();

        // Verify the results
        assertThat(result).isEqualTo("customerId");
    }

    @Test
    void testSetCustomerID() {
        // Setup
        // Run the test
        subscriptionUnderTest.setCustomerID("id");

        // Verify the results
//        verify(mockSubscriptionId).setCustomerID("id");
    }

    @Test
    void testProductIDGetterAndSetter() {
        final String productID = "productID";
        subscriptionUnderTest.setProductID(productID);
        assertThat(subscriptionUnderTest.getProductID()).isEqualTo(productID);
    }
}
