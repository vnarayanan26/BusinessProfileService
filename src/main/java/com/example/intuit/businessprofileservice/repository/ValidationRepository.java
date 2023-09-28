package com.example.intuit.businessprofileservice.repository;

import com.example.intuit.businessprofileservice.model.Validation;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface ValidationRepository extends CrudRepository<Validation, String> {
}
