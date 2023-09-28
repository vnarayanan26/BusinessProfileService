package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.exception.NoSubscriptionsFoundException;
import com.example.intuit.businessprofileservice.exception.ProfileValidationException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AsyncServiceTest {

    @Mock
    private BusinessProfileService mockBusinessProfileService;
    @Mock
    private BusinessProfileRepository mockBusinessProfileRepository;
    @Mock
    private SubscriptionService mockSubscriptionService;

    @Mock
    private ValidationService mockValidationService;

    @Mock
    private AsyncValidatorService mockAsyncValidatorService;

    private AsyncService asyncServiceUnderTest;

    @BeforeEach
    void setUp()
    {
        asyncServiceUnderTest = new AsyncService();
//        asyncServiceUnderTest.businessProfileService = mockBusinessProfileService;
        asyncServiceUnderTest.businessProfileRepository = mockBusinessProfileRepository;
        asyncServiceUnderTest.subscriptionService = mockSubscriptionService;
        asyncServiceUnderTest.validationService = mockValidationService;
        asyncServiceUnderTest.asyncValidatorService = mockAsyncValidatorService;
    }

    @Test
    void testValidateCreateBusinessProfileAsync() {
        // Setup

        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Optional<List<Subscription>> subscriptions = Optional.of(List.of(subscription));

//        AsyncService asyncServiceUnderTestMock = spy(AsyncService.class);
//        asyncServiceUnderTestMock.subscriptionService = mockSubscriptionService;
//        asyncServiceUnderTestMock.businessProfileRepository = mockBusinessProfileRepository;

        when(mockAsyncValidatorService.validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation)).thenReturn(CompletableFuture.completedFuture(true));

        // Run the test
        asyncServiceUnderTest.validateCreateBusinessProfileAsync(businessProfile, existingProfile, currentValidation);

        // Verify the results
        verify(mockValidationService, timeout(1000).times(1)).updateAcceptedValidation(any(Validation.class));
    }

    @Test
    void testValidateCreateBusinessProfileAsync_ValidationFails() {
        // Setup
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Optional<List<Subscription>> subscriptions = Optional.of(List.of(subscription));
//        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID")).thenReturn(subscriptions);

//
//        AsyncService asyncServiceUnderTestMock = spy(AsyncService.class);
//        asyncServiceUnderTestMock.businessProfileService = mockBusinessProfileService;
//        asyncServiceUnderTestMock.subscriptionService = mockSubscriptionService;
//        asyncServiceUnderTestMock.businessProfileRepository = mockBusinessProfileRepository;

//        doReturn(CompletableFuture.completedFuture(false)).when(asyncServiceUnderTestMock).validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation);
        when(mockAsyncValidatorService.validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation)).thenReturn(CompletableFuture.completedFuture(false));

        asyncServiceUnderTest.validateCreateBusinessProfileAsync(businessProfile, existingProfile, currentValidation);
        // Verify the results
        verify(mockValidationService, timeout(1000).times(1)).updateRejectedValidation(any(Validation.class));

    }

    @Test
    void testValidateUpdateBusinessProfileAsync_ValidationFails() {
        // Setup

        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        final Optional<List<Subscription>> subscriptions = Optional.of(List.of(subscription));


        AsyncService asyncServiceUnderTestMock = spy(AsyncService.class);
//        asyncServiceUnderTestMock.businessProfileService = mockBusinessProfileService;
        asyncServiceUnderTestMock.subscriptionService = mockSubscriptionService;
        asyncServiceUnderTestMock.businessProfileRepository = mockBusinessProfileRepository;

//        doReturn(CompletableFuture.completedFuture(false)).when(asyncServiceUnderTestMock).validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation);
        when(mockAsyncValidatorService.validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation)).thenReturn(CompletableFuture.completedFuture(false));

        asyncServiceUnderTest.validateCreateBusinessProfileAsync(businessProfile, existingProfile, currentValidation);

        // Verify the results
        verify(mockValidationService, timeout(1000).times(1)).updateRejectedValidation(any(Validation.class));




    }



    @Test
    void testValidateUpdateBusinessProfileAsync() {
        // Setup
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");

        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestID");
        currentValidation.setTimestamp(0L);


        when(mockAsyncValidatorService.validateProfileCreateOrUpdate(businessProfile,existingProfile,currentValidation)).thenReturn(CompletableFuture.completedFuture(true));

        // Run the test
        asyncServiceUnderTest.validateUpdateBusinessProfileAsync(businessProfile, existingProfile, currentValidation);

        // Verify the results
        verify(mockValidationService, timeout(1000).times(1)).updateAcceptedValidation(any(Validation.class));

    }




}
