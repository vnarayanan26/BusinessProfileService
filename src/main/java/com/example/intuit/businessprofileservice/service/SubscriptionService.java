package com.example.intuit.businessprofileservice.service;

import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.example.intuit.businessprofileservice.exception.SubscriptionNotFoundException;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.repository.SubscriptionRepository;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Optional<Subscription> getSubscriptionById(String id) {
        return subscriptionRepository.findById(id);
    }



    public List<Subscription> getAllSubscriptions() {
        return (List<Subscription>) subscriptionRepository.findAll();
    }

    public Optional<List<Subscription>> getAllSubscriptionsByCustomerId(String customerId) {
        return subscriptionRepository.findByCustomerID(customerId);
    }

    public Optional<Subscription> updateSubscription(String id, Subscription subscription) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(id);
        if (existingSubscription.isPresent()) {
            return Optional.of(subscriptionRepository.save(subscription));
        } else {
            throw new SubscriptionNotFoundException(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE);
        }
    }

    public void deleteSubscription(String id) {
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(id);
        if (existingSubscription.isPresent()) {
            subscriptionRepository.deleteById(id);
        } else {
            throw new SubscriptionNotFoundException(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE);
        }
    }

}
