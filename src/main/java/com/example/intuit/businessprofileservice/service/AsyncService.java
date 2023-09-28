package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.exception.NoSubscriptionsFoundException;
import com.example.intuit.businessprofileservice.exception.ProfileValidationException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AsyncService {

    @Autowired BusinessProfileRepository businessProfileRepository;

    @Autowired SubscriptionService subscriptionService;

    @Autowired ValidationService validationService;

    @Autowired AsyncValidatorService asyncValidatorService;

    @Async("threadPoolTaskExecutor")
    void validateCreateBusinessProfileAsync(
            BusinessProfile businessProfile,
            BusinessProfile existingProfile,
            Validation currentValidation) {
        log.error(
                "Execute method validateCreateBusinessProfileAsync "
                        + Thread.currentThread().getName());
        asyncValidatorService
                .validateProfileCreateOrUpdate(businessProfile, existingProfile, currentValidation)
                .thenAcceptAsync(
                        validated -> {
                            log.debug("Validated value: " + validated);
                            if (validated) {
                                validationService.updateAcceptedValidation(currentValidation);
                                businessProfileRepository.save(businessProfile);
                            } else {
                                validationService.updateRejectedValidation(currentValidation);
                                throw new ProfileValidationException(
                                        "Profile Update Validation Failed");
                            }
                        })
                .exceptionally(
                        exception -> {
                            if (exception instanceof ProfileValidationException) {
                                log.error("Profile Validation failed");
                            }
                            return null;
                        });
    }

    @Async
    void validateUpdateBusinessProfileAsync(
            BusinessProfile businessProfile,
            BusinessProfile existingProfile,
            Validation currentValidation) {
        log.error(
                "Execute method validateUpdateBusinessProfileAsync "
                        + Thread.currentThread().getName());
        asyncValidatorService
                .validateProfileCreateOrUpdate(businessProfile, existingProfile, currentValidation)
                .thenAcceptAsync(
                        validated -> {
                            if (validated) {
                                BusinessProfileUtil.updateBusinessProfileAttributes(
                                        existingProfile, businessProfile);
                                validationService.updateAcceptedValidation(currentValidation);
                                businessProfileRepository.save(existingProfile);

                            } else {
                                validationService.updateRejectedValidation(currentValidation);
                                throw new ProfileValidationException(
                                        "Profile Update Validation Failed");
                            }
                        });
    }
}
