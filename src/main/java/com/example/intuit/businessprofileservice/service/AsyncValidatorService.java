package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.exception.NoSubscriptionsFoundException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AsyncValidatorService {

    @Autowired SubscriptionService subscriptionService;

    @Autowired ValidationService validationService;

    @Autowired BusinessProfileRepository businessProfileRepository;

    CompletableFuture<Boolean> validateProfileCreateOrUpdate(
            BusinessProfile businessProfile,
            BusinessProfile existingProfile,
            Validation currentValidation) {

        return CompletableFuture.supplyAsync(
                        () -> {
                            log.error(
                                    "Execute method validateProfileCreateOrUpdate getSubscriptionsByCustomerIdAsync "
                                            + Thread.currentThread().getName());

                            List<Subscription> subscriptions =
                                    subscriptionService
                                            .getAllSubscriptionsByCustomerId(
                                                    existingProfile.getCustomerID())
                                            .orElseThrow(
                                                    () ->
                                                            new NoSubscriptionsFoundException(
                                                                    Constants
                                                                            .NO_SUBSCRIPTIONS_FOUND_MESSAGE));
                            if (subscriptions.isEmpty())
                                throw new NoSubscriptionsFoundException(
                                        Constants.NO_SUBSCRIPTIONS_FOUND_MESSAGE);
                            subscriptions.forEach(
                                    s -> log.error("Subscription " + s.getProductID()));
                            return subscriptions;
                        })
                .handle(
                        (subscriptions, exception) -> {
                            if (exception != null) {
                                log.error("Handling " + (exception.getCause().getClass()));
                                if (exception instanceof NoSubscriptionsFoundException) {
                                    log.error("No subscription exception block reached");
                                    BusinessProfileUtil.updateBusinessProfileAttributes(
                                            existingProfile, businessProfile);
                                    validationService.updateAcceptedValidation(currentValidation);
                                    businessProfileRepository.save(existingProfile);
                                }
                                return List.of();
                            }
                            return subscriptions;
                        })
                .thenApplyAsync(
                        subscriptions ->
                                subscriptions.stream()
                                        .filter(Subscription.class::isInstance)
                                        .map(Subscription.class::cast)
                                        .map(Subscription::getProductID)
                                        .collect(Collectors.toSet()))
                .thenComposeAsync(
                        products ->
                                CompletableFuture.supplyAsync(
                                        () ->
                                                products.stream()
                                                        .map(
                                                                BusinessProfileUtil
                                                                        ::validateSubscription)
                                                        .collect(Collectors.toList()),
                                        Executors.newCachedThreadPool()))
                .thenApplyAsync(
                        validationResults -> {
                            // Check if all validations were successful
                            log.debug("ValidaionResults size: " + validationResults.size());
                            validationResults.forEach(s -> log.debug("\n " + s));
                            return validationResults.stream().allMatch(Boolean::booleanValue);
                        });
    }
}
