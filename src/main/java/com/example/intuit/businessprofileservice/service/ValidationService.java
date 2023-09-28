package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.ValidationRepository;
import com.example.intuit.businessprofileservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValidationService {

    @Autowired
    ValidationRepository validationRepository;

    public List<Validation> getAllValidationStatuses() {
        return (List<Validation>) validationRepository.findAll();
    }

    public Optional<Validation> getValidationStatusById(String id) {
        return validationRepository.findById(id);
    }

    public Validation createValidationStatus(Validation validation) {
        return validationRepository.save(validation);
    }

    public Optional<Validation> updateValidationStatus(String id, Validation newStatus) {
        return validationRepository.findById(id)
                .map(existingStatus -> {
                    // Update the attributes you want to change
                    existingStatus.setOverallValidationStatus(newStatus.getOverallValidationStatus());
                    return validationRepository.save(existingStatus);
                });
    }

    public void deleteValidationStatus(String id) {
        validationRepository.deleteById(id);
    }

    void updateRejectedValidation(Validation currentValidation) {
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Rejected);
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        updateValidationStatus(currentValidation.getRequestID(), currentValidation);
    }

    void updateAcceptedValidation(Validation currentValidation) {
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Accepted);
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        updateValidationStatus(currentValidation.getRequestID(), currentValidation);
    }

    Validation createInProgressValidation(BusinessProfile businessProfile, Constants.ValidationType validationType) {
        Validation currentValidation = new Validation();
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setCustomerID(businessProfile.getCustomerID());
        currentValidation.setRequestID(UUID.randomUUID().toString());
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        currentValidation.setValidationType(validationType);
        createValidationStatus(currentValidation);
        return currentValidation;
    }
}
