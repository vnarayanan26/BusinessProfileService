package com.example.intuit.businessprofileservice.service;

import com.example.intuit.businessprofileservice.entity.ProfileRevisionValidationMessage;
import com.example.intuit.businessprofileservice.model.ProfileRevisionValidation;
import com.example.intuit.businessprofileservice.model.ProfileRevisionValidationId;
import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.repository.ProfileRevisionValidationDA;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class ProfileRevisionValidationService {

    @Autowired private final ValidationService validationService;

    @Autowired private final ProfileRevisionValidationDA profileRevisionValidationDA;



    @Transactional
    public void validate(ProfileRevisionValidationMessage message) throws Exception {
        ProfileRevisionValidationId profileRevisionValidationId = new ProfileRevisionValidationId();
        profileRevisionValidationId.setValidationId(message.getRevisionId());
        profileRevisionValidationId.setProfileValidationId(message.getProfileValidationId());
        ProfileRevisionValidation profileRevisionValidation = profileRevisionValidationDA.getProfileRevisionValidationById(
                        profileRevisionValidationId)
                .filter(revisionValidation -> Constants.ValidationStatus.In_Progress.equals(
                        revisionValidation.getStatus()))
                .orElseThrow(() -> new Exception("No Such Profile Revision found"));

        if (message.isToAccept()) {
            profileRevisionValidation.setStatus(Constants.ValidationStatus.Accepted);
        } else {
            profileRevisionValidation.setStatus(Constants.ValidationStatus.Rejected);
        }

        // Save the validation
        profileRevisionValidationDA.save(profileRevisionValidation);

        checkIfAllUpdated(message.getProfileValidationId(), message.getRevisionId());
    }

    @Transactional
    public void checkIfAllUpdated(String profileId, String revisionId) {
        Constants.ValidationStatus validationStatus = profileRevisionValidationDA.getStatusByValidationId(
                revisionId);
        if (Constants.ValidationStatus.In_Progress.equals(validationStatus)) {
            return;
        }
        Validation validation =
                validationService
                        .getValidationStatusById(revisionId).orElseThrow();

        if (!Constants.ValidationStatus.In_Progress.equals(validation.getOverallValidationStatus())) {
            return;
        }
        validation.setOverallValidationStatus(validationStatus);
        validationService.updateValidation(revisionId, validation);
    }
}
