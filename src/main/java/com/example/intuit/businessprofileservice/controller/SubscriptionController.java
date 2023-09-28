package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.exception.SubscriptionNotFoundException;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.service.SubscriptionService;
import com.example.intuit.businessprofileservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {
    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<Subscription> createSubscription(@RequestBody Subscription subscription) {
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        return ResponseEntity.ok(createdSubscription);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscription> getSubscriptionById(@PathVariable String id) {
        Optional<Subscription> subscription = subscriptionService.getSubscriptionById(id);
        return subscription.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new SubscriptionNotFoundException(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE));
    }

    @GetMapping
    public ResponseEntity<List<Subscription>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionService.getAllSubscriptions();
        return new ResponseEntity<>(subscriptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable String id, @RequestBody Subscription subscription) {
        Optional<Subscription> updatedSubscription = subscriptionService.updateSubscription(id, subscription);
        return updatedSubscription.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseThrow(() -> new SubscriptionNotFoundException(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable String id) {
        subscriptionService.deleteSubscription(id);
        return ResponseEntity.noContent().build();
    }
}
