package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.config.KafkaProducer;
import com.example.intuit.businessprofileservice.config.KafkaProducerConfig;
import com.example.intuit.businessprofileservice.config.Properties;
import com.example.intuit.businessprofileservice.entity.ProfileRevisionValidationMessage;
import com.example.intuit.businessprofileservice.entity.response.CreateUpdateProfileResponse;
import com.example.intuit.businessprofileservice.exception.*;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.ProfileRevisionValidation;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.repository.ProfileRevisionValidationDA;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessProfileService {

    @Autowired private Properties properties;

    @Autowired private KafkaProducerConfig kafkaConfig;

    @Autowired private KafkaProducer kafkaProducer;

    @Autowired private BusinessProfileRepository businessProfileRepository;

    @Autowired private SubscriptionService subscriptionService;

    @Autowired private ValidationService validationService;

    @Autowired private AsyncService asyncService;

    @Autowired private ProfileRevisionValidationDA profileRevisionValidationDA;

    @Transactional
    public CreateUpdateProfileResponse createBusinessProfile(
            final BusinessProfile businessProfile) {

        checkIfBusinessProfileAlreadyExists(businessProfile.getEmail());

        boolean validated = false;

        businessProfile.setCustomerID(UUID.randomUUID().toString());
        Validation currentValidation =
                validationService.buildInProgressValidation(
                        businessProfile, Constants.ValidationType.CREATE);
        log.debug("Execute method createBusinessProfile " + Thread.currentThread().getName());
        if (properties.getUseMessagingEvents()) {
            List<Subscription> subscriptions =
                    subscriptionService
                            .getAllSubscriptionsByCustomerId(businessProfile.getCustomerID())
                            .orElseThrow(
                                    () ->
                                            new NoSubscriptionsFoundException(
                                                    Constants.NO_SUBSCRIPTIONS_FOUND_MESSAGE));
            Set<String> products =
                    subscriptions.stream()
                            .map(Subscription::getProductID)
                            .collect(Collectors.toSet());

            if (products.isEmpty()) {
                currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Accepted);
                validationService.createValidation(currentValidation);
                businessProfileRepository.save(businessProfile);
            } else {
                validationService.createValidation(currentValidation);
                notifyAllSubscribedProductsForValidation(products, currentValidation);
            }
        } else {
            asyncService.validateCreateBusinessProfileAsync(
                    businessProfile, businessProfile, currentValidation);
        }
        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId(businessProfile.getCustomerID());
        createUpdateProfileResponse.setValidationRequestId(currentValidation.getRequestID());

        return createUpdateProfileResponse;
    }

    public List<BusinessProfile> getAllBusinessProfiles() {
        return (List<BusinessProfile>) businessProfileRepository.findAll();
    }

    @CachePut("profileCache")
    public Optional<BusinessProfile> getBusinessProfileById(String customerId) {

        return businessProfileRepository.findById(customerId);
    }

    @Transactional
    public CreateUpdateProfileResponse updateBusinessProfile(
            BusinessProfile businessProfileRevision) throws ProfileNotFoundException {
//        checkIfValidationInProgress(businessProfileRevision.getCustomerID());

        Optional<BusinessProfile> existingProfileOptional =
                businessProfileRepository.findById(businessProfileRevision.getCustomerID());
        if (existingProfileOptional.isPresent()) {
            BusinessProfile existingProfile = existingProfileOptional.get();
            Validation currentValidation =
                    validationService.buildInProgressValidation(
                            businessProfileRevision, Constants.ValidationType.UPDATE);

            if (properties.getUseMessagingEvents()) {
                List<Subscription> subscriptions =
                        subscriptionService
                                .getAllSubscriptionsByCustomerId(existingProfile.getCustomerID())
                                .orElseThrow(
                                        () ->
                                                new NoSubscriptionsFoundException(
                                                        Constants.NO_SUBSCRIPTIONS_FOUND_MESSAGE));
                Set<String> products =
                        subscriptions.stream()
                                .map(Subscription::getProductID)
                                .collect(Collectors.toSet());
                if (products.isEmpty()) {
                    currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Accepted);
                    validationService.createValidation(currentValidation);
                    BusinessProfileUtil.updateBusinessProfileAttributes(existingProfile,businessProfileRevision);
                    businessProfileRepository.save(existingProfile);
                } else {
                    validationService.createValidation(currentValidation);
                    notifyAllSubscribedProductsForValidation(products, currentValidation);
                }
            } else {
                asyncService.validateUpdateBusinessProfileAsync(
                        businessProfileRevision, existingProfile, currentValidation);
            }
            CreateUpdateProfileResponse createUpdateProfileResponse =
                    new CreateUpdateProfileResponse();
            createUpdateProfileResponse.setCustomerId(existingProfile.getCustomerID());
            createUpdateProfileResponse.setValidationRequestId(currentValidation.getRequestID());
            return createUpdateProfileResponse;

        } else {
            throw new ProfileNotFoundException(
                    Constants.PROFILE_NOT_FOUND_MESSAGE + businessProfileRevision.getCustomerID());
        }
    }

    public void deleteBusinessProfile(String customerId) {
        Optional<BusinessProfile> existingProfileOptional =
                businessProfileRepository.findById(customerId);

        if (existingProfileOptional.isPresent()) {
            businessProfileRepository.deleteById(customerId);
        } else {
            throw new ProfileNotFoundException(Constants.PROFILE_NOT_FOUND_MESSAGE + customerId);
        }
    }

    private void checkIfBusinessProfileAlreadyExists(String emailId) {
        // Check already exists and throw
        businessProfileRepository
                .findByEmail(emailId)
                .ifPresent(
                        (existing) -> {
                            throw new ProfileAlreadyExistsException(
                                    Constants.BUSINESS_PROFILE_ALREADY_EXISTS);
                        });
    }

    private void checkIfValidationInProgress(String customerId) {
        validationService
                .getInProgressRevision(customerId)
                .ifPresent(
                        (validation) -> {
                            if (validation
                                    .getOverallValidationStatus()
                                    .equals(Constants.ValidationStatus.In_Progress))
                                throw new RevisionValidationInProgressException(
                                        Constants.BUSINESS_PROFILE_REVISION_IN_PROGRESS);
                        });
    }

    @Transactional
    public void notifyAllSubscribedProductsForValidation(
            Set<String> subscribedProducts, Validation currentValidation) {

        // If the profile hasn't
        if (CollectionUtils.isEmpty(subscribedProducts)) {
            return;
        }

        // Map and save to DB
        List<ProfileRevisionValidation> validationEntities =
                subscribedProducts.stream()
                        .map(
                                subscribedProductId -> {
                                    ProfileRevisionValidation profileRevisionValidation =
                                            new ProfileRevisionValidation();
                                    profileRevisionValidation.setProfileValidationId(UUID.randomUUID().toString());
                                    profileRevisionValidation.setValidationId(
                                            currentValidation.getRequestID());
                                    profileRevisionValidation.setProductId(subscribedProductId);
                                    profileRevisionValidation.setTimestamp(
                                            Timestamp.from(Instant.now()).getTime());
                                    profileRevisionValidation.setStatus(
                                            Constants.ValidationStatus.In_Progress);
                                    return profileRevisionValidation;
                                })
                        .collect(Collectors.toList());
        profileRevisionValidationDA.saveAll(validationEntities);

        // Send message to Kafka
        validationEntities.stream()
                .map(
                        validationEntity ->
                                ProfileRevisionValidationMessage.builder()
                                        .revisionId(validationEntity.getValidationId())
                                        .productId(validationEntity.getProductId())
                                        .profileValidationId(validationEntity.getProfileValidationId())
                                        .toAccept(true)
                                        .build())
                .forEach(
                        message ->
                                kafkaProducer.send(
                                        properties.getBusinessProfileValidationTopic(), message));
    }

    public void acceptBusinessRevision(Validation savedValidation) {
        BusinessProfile existingProfile =
                getBusinessProfileById(savedValidation.getCustomerID())
                        .orElseThrow(
                                () ->
                                        new ProfileNotFoundException(
                                                Constants.PROFILE_NOT_FOUND_MESSAGE));

        BusinessProfileUtil.updateBusinessProfileAttributes(
                existingProfile, savedValidation.getBusinessProfileRevision());

        businessProfileRepository.save(existingProfile);
    }
}
