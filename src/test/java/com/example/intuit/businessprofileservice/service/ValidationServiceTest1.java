package com.example.intuit.businessprofileservice.service;

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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest1 {

    @Mock
    private ValidationRepository mockValidationRepository;


    private ValidationService validationServiceUnderTest;

    @BeforeEach
    void setUp(){
        validationServiceUnderTest = new ValidationService();
        validationServiceUnderTest.validationRepository = mockValidationRepository;
    }

    @Test
    void testGetAllValidationStatuses() {
        // Setup
        // Configure ValidationStatusRepository.findAll(...).
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setRequestID("requestID");
        validation.setTimestamp(0L);
        final Iterable<Validation> validationStatuses = List.of(validation);
        when(mockValidationRepository.findAll()).thenReturn(validationStatuses);

        // Run the test
        final List<Validation> result = validationServiceUnderTest.getAllValidationStatuses();

        // Verify the results
    }

    @Test
    void testGetAllValidationStatuses_ValidationStatusRepositoryReturnsNoItems() {
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
        // Configure ValidationStatusRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setRequestID("requestID");
        validation1.setTimestamp(0L);
        final Optional<Validation> validationStatus = Optional.of(validation1);
        when(mockValidationRepository.findById("id")).thenReturn(validationStatus);

        // Run the test
        final Optional<Validation> result = validationServiceUnderTest.getValidationStatusById("id");

        // Verify the results
    }

    @Test
    void testGetValidationStatusById_ValidationStatusRepositoryReturnsAbsent() {
        // Setup
        when(mockValidationRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final Optional<Validation> result = validationServiceUnderTest.getValidationStatusById("id");

        // Verify the results
        assertThat(result).isEmpty();
    }

    @Test
    void testCreateValidationStatus() {
        // Setup
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setRequestID("requestID");
        validation.setTimestamp(0L);

        // Configure ValidationStatusRepository.save(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setRequestID("requestID");
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
        newStatus.setRequestID("requestID");
        newStatus.setTimestamp(0L);

        // Configure ValidationStatusRepository.findById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setRequestID("requestID");
        validation1.setTimestamp(0L);
        final Optional<Validation> validationStatus = Optional.of(validation1);
        when(mockValidationRepository.findById("id")).thenReturn(validationStatus);

        // Configure ValidationStatusRepository.save(...).
        final Validation validation2 = new Validation();
        validation2.setCustomerID("customerID");
        validation2.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation2.setRequestID("requestID");
        validation2.setTimestamp(0L);
        when(mockValidationRepository.save(any(Validation.class))).thenReturn(validation2);

        // Run the test
        final Optional<Validation> result = validationServiceUnderTest.updateValidation("id",
                newStatus);

        // Verify the results
    }

    @Test
    void testUpdateValidationStatus_ValidationStatusRepositoryFindByIdReturnsAbsent() {
        // Setup
        final Validation newStatus = new Validation();
        newStatus.setCustomerID("customerID");
        newStatus.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        newStatus.setRequestID("requestID");
        newStatus.setTimestamp(0L);

        when(mockValidationRepository.findById("id")).thenReturn(Optional.empty());

        // Run the test
        final Optional<Validation> result = validationServiceUnderTest.updateValidation("id",
                newStatus);

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
        currentValidation.setRequestID("requestId");
        currentValidation.setTimestamp(0L);
        when(mockValidationRepository.findById("requestId")).thenReturn(Optional.of(currentValidation));

        // Run the test
        validationServiceUnderTest.updateRejectedValidation(currentValidation);

        // Verify the results
        verify(mockValidationRepository).save(
                any(Validation.class));
    }

    @Test
    void testUpdateAcceptedValidation() {
        // Setup
        final Validation currentValidation = new Validation();
        currentValidation.setCustomerID("customerID");
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setRequestID("requestId");
        currentValidation.setTimestamp(0L);

        when(mockValidationRepository.findById("requestId")).thenReturn(Optional.of(currentValidation));

        // Run the test
        validationServiceUnderTest.updateAcceptedValidation(currentValidation);

        // Verify the results
        verify(mockValidationRepository).save(
                any(Validation.class));
    }


}
