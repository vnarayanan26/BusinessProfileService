package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.ProfileRevisionValidation;
import com.example.intuit.businessprofileservice.model.ProfileRevisionValidationId;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ProfileRevisionValidationRepository extends CrudRepository<ProfileRevisionValidation, ProfileRevisionValidationId> {
    List<ProfileRevisionValidation> findByValidationId(String validationId);
}
