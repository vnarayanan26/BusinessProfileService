package com.example.intuit.businessprofileservice.service;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.example.intuit.businessprofileservice.exception.ProfileNotFoundException;
import com.example.intuit.businessprofileservice.exception.SubscriptionNotFoundException;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.repository.SubscriptionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private SubscriptionRepository mockSubscriptionRepository;

    @Test
    void testCreateSubscription() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");

        // Configure SubscriptionRepository.save(...).
        final Subscription subscription1 = new Subscription();
        subscription1.setProductID("productID");
        when(mockSubscriptionRepository.save(any(Subscription.class))).thenReturn(subscription1);

        // Run the test
        final Subscription result = subscriptionServiceUnderTest.createSubscription(subscription);

        // Verify the results
    }

    @Test
    void testGetSubscriptionById() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);

        // Configure SubscriptionRepository.findById(...).
        final Subscription subscription1 = new Subscription();
        subscription1.setProductID("productID");
        final Optional<Subscription> subscription = Optional.of(subscription1);
        when(mockSubscriptionRepository.findById("id")).thenReturn(subscription);

        // Run the test
        final Optional<Subscription> result = subscriptionServiceUnderTest.getSubscriptionById("id");

        // Verify the results
    }

    @Test
    void testGetSubscriptionById_SubscriptionRepositoryReturnsAbsent() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        when(mockSubscriptionRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final Optional<Subscription> result = subscriptionServiceUnderTest.getSubscriptionById("id");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testGetAllSubscriptions() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);

        // Configure SubscriptionRepository.findAll(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Iterable<Subscription> subscriptions = List.of(subscription);
        when(mockSubscriptionRepository.findAll()).thenReturn(subscriptions);

        // Run the test
        final List<Subscription> result = subscriptionServiceUnderTest.getAllSubscriptions();

        // Verify the results
    }

    @Test
    void testGetAllSubscriptions_SubscriptionRepositoryReturnsNoItems() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        when(mockSubscriptionRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Subscription> result = subscriptionServiceUnderTest.getAllSubscriptions();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAllSubscriptionsByCustomerId() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);

        // Configure SubscriptionRepository.findByCustomerID(...).
//        final PaginatedScanList<Subscription> mockPaginatedScanList = mock(PaginatedScanList.class);
//        when(mockSubscriptionRepository.findByCustomerID("customerId")).thenReturn(mockPaginatedScanList);

        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Optional<List<Subscription>> subscriptions = Optional.of(List.of(subscription));
        when(mockSubscriptionRepository.findByCustomerID("customerId")).thenReturn(subscriptions);

        // Run the test
        final List<Subscription> result = subscriptionServiceUnderTest.getAllSubscriptionsByCustomerId(
                "customerId").get();

        // Verify the results
        assertThat(result).isEqualTo(subscriptions.get());
    }

    @Test
    void testGetAllSubscriptionsByCustomerId_SubscriptionRepositoryReturnsNoItems() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);

        // Configure SubscriptionRepository.findByCustomerID(...).
        final List<Subscription> mockList = mock(List.class);
        when(mockSubscriptionRepository.findByCustomerID("customerId")).thenReturn(Optional.ofNullable(mockList));

        // Run the test
        final List<Subscription> result = subscriptionServiceUnderTest.getAllSubscriptionsByCustomerId(
                "customerId").get();

        // Verify the results
        assertThat(result.size()).isEqualTo(0);
    }

    @Test
    void testUpdateSubscription() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");

        // Configure SubscriptionRepository.findById(...).
        final Subscription subscription2 = new Subscription();
        subscription2.setProductID("productID");
        final Optional<Subscription> subscription1 = Optional.of(subscription2);
        when(mockSubscriptionRepository.findById("id")).thenReturn(subscription1);

        // Configure SubscriptionRepository.save(...).
        final Subscription subscription3 = new Subscription();
        subscription3.setProductID("productID");
        when(mockSubscriptionRepository.save(any(Subscription.class))).thenReturn(subscription3);

        // Run the test
        final Optional<Subscription> result = subscriptionServiceUnderTest.updateSubscription("id", subscription);

        // Verify the results
    }

    @Test
    void testUpdateSubscription_SubscriptionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");

        when(mockSubscriptionRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test

        // Verify the results
        Assertions.assertThrows(SubscriptionNotFoundException.class,() -> subscriptionServiceUnderTest.updateSubscription("id", subscription));

    }

    @Test
    void testDeleteSubscription() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);

        // Configure SubscriptionRepository.findById(...).
        final Subscription subscription1 = new Subscription();
        subscription1.setProductID("productID");
        final Optional<Subscription> subscription = Optional.of(subscription1);
        when(mockSubscriptionRepository.findById("id")).thenReturn(subscription);

        // Run the test
        subscriptionServiceUnderTest.deleteSubscription("id");

        // Verify the results
        verify(mockSubscriptionRepository).findById("id");
        verify(mockSubscriptionRepository).deleteById("id");
    }

    @Test
    void testDeleteSubscription_SubscriptionRepositoryFindByIdReturnsAbsent() {
        // Setup
        final SubscriptionService subscriptionServiceUnderTest = new SubscriptionService(mockSubscriptionRepository);
        when(mockSubscriptionRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
//        subscriptionServiceUnderTest.deleteSubscription("id");

        // Verify the results
        Assertions.assertThrows(SubscriptionNotFoundException.class,() -> subscriptionServiceUnderTest.deleteSubscription("id"));

    }
}
