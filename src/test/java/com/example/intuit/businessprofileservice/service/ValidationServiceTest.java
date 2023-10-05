package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.ValidationRepository;
import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @Mock private ValidationRepository mockValidationRepository;

    private ValidationService validationServiceUnderTest;

    @BeforeEach
    void setUp() {
        validationServiceUnderTest = new ValidationService();
        validationServiceUnderTest.validationRepository = mockValidationRepository;
    }

    @Test
    void testGetAllValidationStatuses() {
        // Setup
        // Configure ValidationRepository.findAll(...).
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setValidationType(Constants.ValidationType.CREATE);
        validation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation.setTimestamp(0L);
        final Iterable<Validation> validations = List.of(validation);
        when(mockValidationRepository.findAll()).thenReturn(validations);

        // Run the test
        final List<Validation> result = validationServiceUnderTest.getAllValidationStatuses();

        // Verify the results
    }

    @Test
    void testGetAllValidationStatuses_ValidationRepositoryReturnsNoItems() {
        // Setup
        when(mockValidationRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Validation> result = validationServiceUnderTest.getAllValidationStatuses();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetValidationStatusById() {
        // Setup
        // Configure ValidationRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        validation1.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation1.setTimestamp(0L);
        final Optional<Validation> validation = Optional.of(validation1);
        when(mockValidationRepository.findById("id")).thenReturn(validation);

        // Run the test
        final Optional<Validation> result =
                validationServiceUnderTest.getValidationStatusById("id");

        // Verify the results
    }

    @Test
    void testGetValidationStatusById_ValidationRepositoryReturnsAbsent() {
        // Setup
        when(mockValidationRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final Optional<Validation> result =
                validationServiceUnderTest.getValidationStatusById("id");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testCreateValidationStatus() {
        // Setup
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setValidationType(Constants.ValidationType.CREATE);
        validation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation.setTimestamp(0L);

        // Configure ValidationRepository.save(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        validation1.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation1.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation1);

        // Run the test
        final Validation result = validationServiceUnderTest.createValidation(validation);

        // Verify the results
    }

    @Test
    void testUpdateValidationStatus() {
        // Setup
        final Validation newStatus = new Validation();
        newStatus.setCustomerID("customerID");
        newStatus.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        newStatus.setValidationType(Constants.ValidationType.CREATE);
        newStatus.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        newStatus.setTimestamp(0L);

        // Configure ValidationRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        validation1.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation1.setTimestamp(0L);
        final Optional<Validation> validation = Optional.of(validation1);
        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(validation);

        // Configure ValidationRepository.save(...).
        final Validation validation2 = new Validation();
        validation2.setCustomerID("customerID");
        validation2.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation2.setValidationType(Constants.ValidationType.CREATE);
        validation2.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation2.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation2);

        // Run the test
        final Optional<Validation> result =
                validationServiceUnderTest.updateValidation(
                        "e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f", newStatus);

        // Verify the results
    }

    @Test
    void testUpdateValidationStatus_ValidationRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Validation newStatus = new Validation();
        newStatus.setCustomerID("customerID");
        newStatus.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        newStatus.setValidationType(Constants.ValidationType.CREATE);
        newStatus.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        newStatus.setTimestamp(0L);

        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(Optional.empty());

        // Run the test
        final Optional<Validation> result =
                validationServiceUnderTest.updateValidation(
                        "e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f", newStatus);

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testDeleteValidationStatus() {
        // Setup
        // Run the test
        validationServiceUnderTest.deleteValidation("id");

        // Verify the results
        verify(mockValidationRepository).deleteById("id");
    }

    @Test
    void testUpdateRejectedValidation() {
        // Setup
        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setValidationType(Constants.ValidationType.CREATE);
        currentValidation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        currentValidation.setTimestamp(0L);

        // Configure ValidationRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        validation1.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation1.setTimestamp(0L);
        final Optional<Validation> validation = Optional.of(validation1);
        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(validation);

        // Configure ValidationRepository.save(...).
        final Validation validation2 = new Validation();
        validation2.setCustomerID("customerID");
        validation2.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation2.setValidationType(Constants.ValidationType.CREATE);
        validation2.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation2.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation2);

        // Run the test
        validationServiceUnderTest.updateRejectedValidation(currentValidation);

        // Verify the results
    }

    @Test
    void testUpdateRejectedValidation_ValidationRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setValidationType(Constants.ValidationType.CREATE);
        currentValidation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        currentValidation.setTimestamp(0L);

        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(Optional.empty());

        // Run the test
        validationServiceUnderTest.updateRejectedValidation(currentValidation);

        // Verify the results
    }

    @Test
    void testUpdateAcceptedValidation() {
        // Setup
        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setValidationType(Constants.ValidationType.CREATE);
        currentValidation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        currentValidation.setTimestamp(0L);

        // Configure ValidationRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        validation1.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation1.setTimestamp(0L);
        final Optional<Validation> validation = Optional.of(validation1);
        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(validation);

        // Configure ValidationRepository.save(...).
        final Validation validation2 = new Validation();
        validation2.setCustomerID("customerID");
        validation2.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation2.setValidationType(Constants.ValidationType.CREATE);
        validation2.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation2.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation2);

        // Run the test
        validationServiceUnderTest.updateAcceptedValidation(currentValidation);

        // Verify the results
    }

    @Test
    void testUpdateAcceptedValidation_ValidationRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setValidationType(Constants.ValidationType.CREATE);
        currentValidation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        currentValidation.setTimestamp(0L);

        when(mockValidationRepository.findById("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f"))
                .thenReturn(Optional.empty());

        // Run the test
        validationServiceUnderTest.updateAcceptedValidation(currentValidation);

        // Verify the results
    }

    @Test
    void testCreateInProgressValidation() {
        // Setup
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerID");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        // Configure ValidationRepository.save(...).
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setValidationType(Constants.ValidationType.CREATE);
        validation.setRequestID("e5e7656b-4e4a-4ceb-a9b3-fbd0b8c89f2f");
        validation.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation);

        // Run the test
        final Validation result =
                validationServiceUnderTest.buildInProgressValidation(
                        businessProfile, Constants.ValidationType.CREATE);

        // Verify the results
    }
}
