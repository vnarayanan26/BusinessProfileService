package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.entity.Address;
import com.example.intuit.businessprofileservice.entity.response.CreateUpdateProfileResponse;
import com.example.intuit.businessprofileservice.exception.ProfileNotFoundException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.BusinessProfileRepository;
import com.example.intuit.businessprofileservice.util.BusinessProfileUtil;
import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessProfileServiceTest {

    @Mock
    private BusinessProfileRepository mockBusinessProfileRepository;
    @Mock
    private SubscriptionService mockSubscriptionService;
    @Mock
    private ValidationService mockValidationService;

    @Mock
    private AsyncService mockAsyncService;

    @InjectMocks
    private BusinessProfileService businessProfileServiceUnderTest;

    @Test
    void testCreateBusinessProfile() {
        // Setup
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

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
         Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestId");
        currentValidation.setTimestamp(0L);
        currentValidation.setValidationType(Constants.ValidationType.CREATE);
        when(mockValidationService.buildInProgressValidation(businessProfile, Constants.ValidationType.CREATE)).thenReturn(currentValidation);

        // Run the test
        final CreateUpdateProfileResponse result = businessProfileServiceUnderTest.createBusinessProfile(businessProfile);

        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId("customerID");
        createUpdateProfileResponse.setValidationRequestId("requestId");
        // Verify the results
        assertThat(result.getValidationRequestId()).isEqualTo(currentValidation.getRequestID());
    }

//    @Test
//    void testCreateBusinessProfile_ValidationFails() {
//        // Setup
//        final BusinessProfile businessProfile = new BusinessProfile();
//        businessProfile.setCustomerID("customerID");
//        businessProfile.setCompanyName("companyName");
//        businessProfile.setLegalName("legalName");
//        businessProfile.setEmail("email");
//        businessProfile.setWebsite("website");
//        final Address businessAddress = new Address();
//        businessProfile.setBusinessAddress(businessAddress);
//        final Address legalAddress = new Address();
//        businessProfile.setLegalAddress(legalAddress);
//        businessProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
//
//        Validation validation = new Validation();
//
//        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
////        List<Subscription> subscriptions = new ArrayList<>();
////        Subscription subscription = new Subscription();
////        subscription.setId("id");
////        subscription.setProductID("productId");
////        subscription.setCustomerID("customerId");
////        subscriptions.add(subscription);
////        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID")).thenReturn(subscriptions);]
//        when(mockAsyncService.validateProfileCreateOrUpdate(businessProfile, businessProfile, validation).isDone()).thenReturn(false);
//
//        // Verify the results
//        Assertions.assertThrows(ProfileValidationException.class,() -> businessProfileServiceUnderTest.createBusinessProfile(businessProfile));
//
//
//
//    }


    @Test
    void testGetAllBusinessProfiles() {
        // Setup
        // Configure BusinessProfileRepository.findAll(...).
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
        final Iterable<BusinessProfile> businessProfiles = List.of(businessProfile);
        when(mockBusinessProfileRepository.findAll()).thenReturn(businessProfiles);

        // Run the test
        final List<BusinessProfile> result = businessProfileServiceUnderTest.getAllBusinessProfiles();

        // Verify the results
    }

    @Test
    void testGetAllBusinessProfiles_BusinessProfileRepositoryReturnsNoItems() {
        // Setup
        when(mockBusinessProfileRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<BusinessProfile> result = businessProfileServiceUnderTest.getAllBusinessProfiles();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetBusinessProfileById() {
        // Setup
        // Configure BusinessProfileRepository.findById(...).
        final BusinessProfile businessProfile1 = new BusinessProfile();
        businessProfile1.setCustomerID("customerID");
        businessProfile1.setCompanyName("companyName");
        businessProfile1.setLegalName("legalName");
        businessProfile1.setEmail("email");
        businessProfile1.setWebsite("website");
        final Address businessAddress = new Address();
        businessProfile1.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        businessProfile1.setLegalAddress(legalAddress);
        businessProfile1.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
        final Optional<BusinessProfile> businessProfile = Optional.of(businessProfile1);
        when(mockBusinessProfileRepository.findById("customerId")).thenReturn(businessProfile);

        // Run the test
        final Optional<BusinessProfile> result = businessProfileServiceUnderTest.getBusinessProfileById("customerId");

        // Verify the results
    }

    @Test
    void testGetBusinessProfileById_BusinessProfileRepositoryReturnsAbsent() {
        // Setup
        when(mockBusinessProfileRepository.findById("customerId")).thenReturn(Optional.empty());

        // Run the test
        final Optional<BusinessProfile> result = businessProfileServiceUnderTest.getBusinessProfileById("customerId");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testUpdateBusinessProfile() {
        // Setup
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

        // Configure BusinessProfileRepository.findById(...).
        final BusinessProfile businessProfile2 = new BusinessProfile();
        businessProfile2.setCustomerID("customerID");
        businessProfile2.setCompanyName("companyName");
        businessProfile2.setLegalName("legalName");
        businessProfile2.setEmail("email");
        businessProfile2.setWebsite("website");
        final Address businessAddress1 = new Address();
        businessProfile2.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        businessProfile2.setLegalAddress(legalAddress1);
        businessProfile2.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
        final Optional<BusinessProfile> businessProfile1 = Optional.of(businessProfile2);
        when(mockBusinessProfileRepository.findById("customerID")).thenReturn(businessProfile1);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
//        final List<Subscription> mockList = mock(List.class);
//        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID")).thenReturn(Optional.ofNullable(mockList));

        // Configure BusinessProfileRepository.save(...).
        final BusinessProfile businessProfile3 = new BusinessProfile();
        businessProfile3.setCustomerID("customerID");
        businessProfile3.setCompanyName("companyName");
        businessProfile3.setLegalName("legalName");
        businessProfile3.setEmail("email");
        businessProfile3.setWebsite("website");
        final Address businessAddress2 = new Address();
        businessProfile3.setBusinessAddress(businessAddress2);
        final Address legalAddress2 = new Address();
        businessProfile3.setLegalAddress(legalAddress2);
//        businessProfile3.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
//        when(mockBusinessProfileRepository.save(any(BusinessProfile.class))).thenReturn(businessProfile3);

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
        Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestId");
        currentValidation.setTimestamp(0L);
        currentValidation.setValidationType(Constants.ValidationType.UPDATE);
        when(mockValidationService.buildInProgressValidation(businessProfile, Constants.ValidationType.UPDATE)).thenReturn(currentValidation);

        // Run the test
        final CreateUpdateProfileResponse result = businessProfileServiceUnderTest.updateBusinessProfile(businessProfile);

        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId("customerID");
        createUpdateProfileResponse.setValidationRequestId("requestId");
        // Verify the results
        assertThat(result.getValidationRequestId()).isEqualTo(currentValidation.getRequestID());
    }

//    @Test
//    void testUpdateBusinessProfile_ValidationFails() {
//        // Setup
//        final BusinessProfile businessProfile = new BusinessProfile();
//        businessProfile.setCustomerID("customerID");
//        businessProfile.setCompanyName("companyName");
//        businessProfile.setLegalName("legalName");
//        businessProfile.setEmail("email");
//        businessProfile.setWebsite("website");
//        final Address businessAddress = new Address();
//        businessProfile.setBusinessAddress(businessAddress);
//        final Address legalAddress = new Address();
//        businessProfile.setLegalAddress(legalAddress);
//        businessProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
//
//        // Configure BusinessProfileRepository.findById(...).
//        final BusinessProfile businessProfile2 = new BusinessProfile();
//        businessProfile2.setCustomerID("customerID");
//        businessProfile2.setCompanyName("companyName");
//        businessProfile2.setLegalName("legalName");
//        businessProfile2.setEmail("email");
//        businessProfile2.setWebsite("website");
//        final Address businessAddress1 = new Address();
//        businessProfile2.setBusinessAddress(businessAddress1);
//        final Address legalAddress1 = new Address();
//        businessProfile2.setLegalAddress(legalAddress1);
//        businessProfile2.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
//
//        Validation validation = new Validation();
//
//        final Optional<BusinessProfile> businessProfile1 = Optional.of(businessProfile2);
//        when(mockBusinessProfileRepository.findById("customerID")).thenReturn(businessProfile1);
//
//        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
//        when(mockAsyncService.validateProfileCreateOrUpdate(businessProfile, businessProfile, validation).isDone()).thenReturn(false);
//
//        // Verify the results
//        Assertions.assertThrows(ProfileValidationException.class,() -> businessProfileServiceUnderTest.updateBusinessProfile(businessProfile));
//
//    }

    @Test
    void testUpdateBusinessProfile_BusinessProfileRepositoryFindByIdReturnsAbsent() {
        // Setup
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

        when(mockBusinessProfileRepository.findById("customerID")).thenReturn(Optional.empty());

        // Configure SubscriptionService.getAllSubscriptionsByCustomerId(...).
//        final List<Subscription> mockList = mock(List.class);
//        when(mockSubscriptionService.getAllSubscriptionsByCustomerId("customerID")).thenReturn(Optional.ofNullable(mockList));
//


        // Run the test
//        final BusinessProfile result = businessProfileServiceUnderTest.updateBusinessProfile(businessProfile);

//         Verify the results
        Assertions.assertThrows(ProfileNotFoundException.class,() -> businessProfileServiceUnderTest.updateBusinessProfile(businessProfile));
    }



    @Test
    void testDeleteBusinessProfile() {
        // Setup
        // Configure BusinessProfileRepository.findById(...).
        final BusinessProfile businessProfile1 = new BusinessProfile();
        businessProfile1.setCustomerID("customerID");
        businessProfile1.setCompanyName("companyName");
        businessProfile1.setLegalName("legalName");
        businessProfile1.setEmail("email");
        businessProfile1.setWebsite("website");
        final Address businessAddress = new Address();
        businessProfile1.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        businessProfile1.setLegalAddress(legalAddress);
        businessProfile1.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));
        final Optional<BusinessProfile> businessProfile = Optional.of(businessProfile1);
        when(mockBusinessProfileRepository.findById("customerId")).thenReturn(businessProfile);

        // Run the test
        businessProfileServiceUnderTest.deleteBusinessProfile("customerId");

        // Verify the results
        verify(mockBusinessProfileRepository).deleteById("customerId");
    }

    @Test
    void testDeleteBusinessProfile_BusinessProfileRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockBusinessProfileRepository.findById("customerId")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> businessProfileServiceUnderTest.deleteBusinessProfile("customerId"))
                .isInstanceOf(ProfileNotFoundException.class);
    }

    @Test
    void testUpdateBusinessProfileAttributes() {
        // Setup
        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCustomerID("customerID");
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");
        final Address businessAddress = new Address();
        existingProfile.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        existingProfile.setLegalAddress(legalAddress);
        existingProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final BusinessProfile newProfile = new BusinessProfile();
        newProfile.setCustomerID("customerID");
        newProfile.setCompanyName("companyName");
        newProfile.setLegalName("legalName");
        newProfile.setEmail("email");
        newProfile.setWebsite("website");
        final Address businessAddress1 = new Address();
        newProfile.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        newProfile.setLegalAddress(legalAddress1);
        newProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        // Run the test
        BusinessProfileUtil.updateBusinessProfileAttributes(existingProfile, newProfile);

        // Verify the results
    }


}
