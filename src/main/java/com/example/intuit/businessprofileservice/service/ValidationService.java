package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.ValidationRepository;
import com.example.intuit.businessprofileservice.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ValidationService {

    @Autowired
    ValidationRepository validationRepository;

    @Autowired
    BusinessProfileService businessProfileService;


    public List<Validation> getAllValidationStatuses() {
        return (List<Validation>) validationRepository.findAll();
    }

    public Optional<Validation> getValidationStatusById(String id) {
        return validationRepository.findById(id);
    }
    public Optional<Validation> getValidationStatusByCustomerId(String customerId) {
        return validationRepository.findByCustomerID(customerId);
    }

    public Optional<Validation> getInProgressRevision(
            final String customerId) {
        List<Validation> inProgressRevisions = validationRepository
                .findByCustomerIDAndOverallValidationStatus(customerId,
                        Constants.ValidationStatus.In_Progress);

        if (CollectionUtils.isEmpty(inProgressRevisions)) {
            return Optional.empty();
        }

        return inProgressRevisions.stream().findFirst();
    }

    public Validation createValidation(Validation validation) {
        return validationRepository.save(validation);
    }

    public Optional<Validation> updateValidation(String id, Validation updatedValidation) {
        return validationRepository.findById(id)
                .map(existingStatus -> {
                    Constants.ValidationStatus newValidationStatus = updatedValidation.getOverallValidationStatus();

                    // Update the attributes you want to change
                    existingStatus.setOverallValidationStatus(updatedValidation.getOverallValidationStatus());

                    Validation savedValidation =  validationRepository.save(existingStatus);

                    if (Constants.ValidationStatus.Accepted.equals(newValidationStatus) && Constants.ValidationType.UPDATE.equals(savedValidation.getValidationType())) {
                        businessProfileService.acceptBusinessRevision(savedValidation);
                    }
                    return savedValidation;
                });
    }

    public void deleteValidation(String id) {
        validationRepository.deleteById(id);
    }

    void updateRejectedValidation(Validation currentValidation) {
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Rejected);
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        updateValidation(currentValidation.getRequestID(), currentValidation);
    }

    void updateAcceptedValidation(Validation currentValidation) {
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.Accepted);
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        updateValidation(currentValidation.getRequestID(), currentValidation);
    }

    Validation buildInProgressValidation(BusinessProfile businessProfile, Constants.ValidationType validationType) {
        Validation currentValidation = new Validation();
        currentValidation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        currentValidation.setCustomerID(businessProfile.getCustomerID());
        currentValidation.setRequestID(UUID.randomUUID().toString());
        currentValidation.setTimestamp(Timestamp.from(Instant.now()).getTime());
        currentValidation.setValidationType(validationType);
        currentValidation.setBusinessProfileRevision(businessProfile);
        return currentValidation;
    }
}
