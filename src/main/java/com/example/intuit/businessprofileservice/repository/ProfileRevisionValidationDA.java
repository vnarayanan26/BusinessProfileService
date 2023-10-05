package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.ProfileRevisionValidation;
import com.example.intuit.businessprofileservice.model.ProfileRevisionValidationId;
import com.example.intuit.businessprofileservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ProfileRevisionValidationDA {


    private final ProfileRevisionValidationRepository profileRevisionValidationRepository;

    public ProfileRevisionValidation save(ProfileRevisionValidation entityToSave) {
        return profileRevisionValidationRepository.save(entityToSave);
    }


    public List<ProfileRevisionValidation> saveAll(
            Iterable<ProfileRevisionValidation> entities) {
        return (List<ProfileRevisionValidation>) profileRevisionValidationRepository.saveAll(entities);
    }

    public Optional<ProfileRevisionValidation> getProfileRevisionValidationById(ProfileRevisionValidationId id) {
        return profileRevisionValidationRepository.findById(id);
    }

//    public Optional<ProfileRevisionValidation> getProfileValidationByRevisionId(String revisionId) {
//        return profileRevisionValidationRepository.findByRevisionId(revisionId);
//    }

    public Constants.ValidationStatus getStatusByValidationId(String validationId) {
        List<ProfileRevisionValidation> revisionValidations = profileRevisionValidationRepository.findByValidationId(
                validationId);

        if (revisionValidations.stream()
                .allMatch(entity -> Constants.ValidationStatus.Accepted.equals(entity.getStatus()))) {
            return Constants.ValidationStatus.Accepted;
        } else if (revisionValidations.stream()
                .anyMatch(entity -> Constants.ValidationStatus.Rejected.equals(entity.getStatus()))) {
            return Constants.ValidationStatus.Rejected;
        } else if (revisionValidations.stream()
                .anyMatch(entity -> Constants.ValidationStatus.Discarded.equals(entity.getStatus()))) {
            return Constants.ValidationStatus.Discarded;
        }
        return Constants.ValidationStatus.In_Progress;
    }
}
