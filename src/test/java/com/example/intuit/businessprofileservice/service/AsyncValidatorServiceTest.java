package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.entity.Address;
import com.example.intuit.businessprofileservice.exception.NoSubscriptionsFoundException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AsyncValidatorServiceTest {

    @Mock private SubscriptionService mockSubscriptionService;
    @Mock private ValidationService mockValidationService;
    @Mock private BusinessProfileRepository mockBusinessProfileRepository;

    @Test
    void testValidateProfileCreateOrUpdate() throws ExecutionException, InterruptedException {
        // Setup
        final AsyncValidatorService asyncValidatorServiceUnderTest = new AsyncValidatorService();
        asyncValidatorServiceUnderTest.subscriptionService = mockSubscriptionService;
        asyncValidatorServiceUnderTest.validationService = mockValidationService;
        asyncValidatorServiceUnderTest.businessProfileRepository = mockBusinessProfileRepository;
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");
        final Address businessAddress = new Address();
        businessProfile.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        businessProfile.setLegalAddress(legalAddress);
        businessProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");
        final Address businessAddress1 = new Address();
        existingProfile.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        existingProfile.setLegalAddress(legalAddress1);
        existingProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Optional<List<Subscription>> subscriptions = Optional.of(List.of(subscription));
        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID"))
                .thenReturn(subscriptions);

        // Run the test
        final CompletableFuture<Boolean> result =
                asyncValidatorServiceUnderTest.validateProfileCreateOrUpdate(
                        businessProfile, existingProfile, currentValidation);



        // Verify the results
        assertThat(result.get()).isEqualTo(true);
    }

    @Test
    void testValidateProfileCreateOrUpdate_SubscriptionServiceReturnsAbsent() throws ExecutionException, InterruptedException {
        // Setup
        final AsyncValidatorService asyncValidatorServiceUnderTest = new AsyncValidatorService();
        asyncValidatorServiceUnderTest.subscriptionService = mockSubscriptionService;
        asyncValidatorServiceUnderTest.validationService = mockValidationService;
        asyncValidatorServiceUnderTest.businessProfileRepository = mockBusinessProfileRepository;
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");
        final Address businessAddress = new Address();
        businessProfile.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        businessProfile.setLegalAddress(legalAddress);
        businessProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");
        final Address businessAddress1 = new Address();
        existingProfile.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        existingProfile.setLegalAddress(legalAddress1);
        existingProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID"))
                .thenReturn(Optional.empty());

        // Run the test
        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID")).thenReturn(Optional.empty());

        // Run the test
        final CompletableFuture<Boolean> result = asyncValidatorServiceUnderTest.validateProfileCreateOrUpdate(businessProfile,
                existingProfile, currentValidation);

        assertThat(result.get()).isEqualTo(true);
    }

    @Test
    void testValidateProfileCreateOrUpdate_SubscriptionServiceReturnsNoItems() throws ExecutionException, InterruptedException {
        // Setup
        final AsyncValidatorService asyncValidatorServiceUnderTest = new AsyncValidatorService();
        asyncValidatorServiceUnderTest.subscriptionService = mockSubscriptionService;
        asyncValidatorServiceUnderTest.validationService = mockValidationService;
        asyncValidatorServiceUnderTest.businessProfileRepository = mockBusinessProfileRepository;
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");
        final Address businessAddress = new Address();
        businessProfile.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        businessProfile.setLegalAddress(legalAddress);
        businessProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");
        final Address businessAddress1 = new Address();
        existingProfile.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        existingProfile.setLegalAddress(legalAddress1);
        existingProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID"))
                .thenReturn(Optional.of(Collections.emptyList()));

        // Run the test
        final CompletableFuture<Boolean> result = asyncValidatorServiceUnderTest.validateProfileCreateOrUpdate(businessProfile,
                existingProfile, currentValidation);

        assertThat(result.get()).isEqualTo(true);
    }


}
