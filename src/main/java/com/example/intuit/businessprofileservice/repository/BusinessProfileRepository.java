package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.model.Subscription;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface BusinessProfileRepository extends CrudRepository<BusinessProfile, String> {
    Optional<BusinessProfile> findByEmail(String email);
}
