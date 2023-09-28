package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.entity.response.CreateUpdateProfileResponse;
import com.example.intuit.businessprofileservice.exception.NoSubscriptionsFoundException;
import com.example.intuit.businessprofileservice.exception.ProfileNotFoundException;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BusinessProfileService {

    @Autowired
    private BusinessProfileRepository businessProfileRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private AsyncService asyncService;


    public CreateUpdateProfileResponse createBusinessProfile(final BusinessProfile businessProfile) {

        boolean validated = false;

        businessProfile.setCustomerID(UUID.randomUUID().toString());
        Validation currentValidation = validationService.createInProgressValidation(businessProfile, Constants.ValidationType.CREATE);
        log.git ("Execute method createBusinessProfile " + Thread.currentThread().getName());

        asyncService.validateCreateBusinessProfileAsync(businessProfile, businessProfile, currentValidation);

        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId(businessProfile.getCustomerID());
        createUpdateProfileResponse.setValidationRequestId(currentValidation.getRequestID());

        return createUpdateProfileResponse;
    }

    public List<BusinessProfile> getAllBusinessProfiles() {
        return (List<BusinessProfile>) businessProfileRepository.findAll();
    }

    public Optional<BusinessProfile> getBusinessProfileById(String customerId) {

        return businessProfileRepository.findById(customerId);


    }


    public CreateUpdateProfileResponse updateBusinessProfile(BusinessProfile businessProfile) throws ProfileNotFoundException {
        Optional<BusinessProfile> existingProfileOptional = businessProfileRepository.findById(businessProfile.getCustomerID());
        if (existingProfileOptional.isPresent()) {
            BusinessProfile existingProfile = existingProfileOptional.get();
            Validation currentValidation = validationService.createInProgressValidation(businessProfile, Constants.ValidationType.UPDATE);

            asyncService.validateUpdateBusinessProfileAsync(businessProfile, existingProfile, currentValidation);
            CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
            createUpdateProfileResponse.setCustomerId(existingProfile.getCustomerID());
            createUpdateProfileResponse.setValidationRequestId(currentValidation.getRequestID());
            return createUpdateProfileResponse;

        } else {
            throw new ProfileNotFoundException(Constants.PROFILE_NOT_FOUND_MESSAGE + businessProfile.getCustomerID());
        }
    }






    public void deleteBusinessProfile(String customerId) {
        Optional<BusinessProfile> existingProfileOptional = businessProfileRepository.findById(customerId);

        if (existingProfileOptional.isPresent()) {
            businessProfileRepository.deleteById(customerId);
        } else {
            throw new ProfileNotFoundException(Constants.PROFILE_NOT_FOUND_MESSAGE + customerId);
        }
    }


}
