package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.Subscription;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface SubscriptionRepository extends CrudRepository<Subscription, String> {
    Optional<List<Subscription>> findByCustomerID(String customerID);
}
