package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.util.Constants;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface ValidationRepository extends CrudRepository<Validation, String> {
    Optional<Validation> findByCustomerID(String customerId);
    List<Validation> findByCustomerIDAndOverallValidationStatus(
            String customerId,
            Constants.ValidationStatus status);
}
