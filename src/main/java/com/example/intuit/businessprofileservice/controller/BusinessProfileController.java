package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.entity.response.CreateUpdateProfileResponse;
import com.example.intuit.businessprofileservice.exception.ProfileNotFoundException;
import com.example.intuit.businessprofileservice.exception.ProfileValidationException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.service.BusinessProfileService;
import com.example.intuit.businessprofileservice.util.Constants;
import com.example.intuit.businessprofileservice.validator.BusinessProfileValidator;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/profile")
public class BusinessProfileController {


    @Autowired
    private BusinessProfileService businessProfileService;

    @GetMapping
    public ResponseEntity<List<BusinessProfile>> getAllBusinessProfiles() {
        List<BusinessProfile> profiles = businessProfileService.getAllBusinessProfiles();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{customerId}")
    @CircuitBreaker(name = "businessProfileFallback", fallbackMethod = "fallbackForGettingBusinessProfile")
    public ResponseEntity<BusinessProfile> getBusinessProfileById(@PathVariable("customerId") String customerId) {
        Optional<BusinessProfile> profile = businessProfileService.getBusinessProfileById(customerId);

        return profile.map(ResponseEntity::ok).orElseThrow(() -> new ProfileNotFoundException(Constants.PROFILE_NOT_FOUND_MESSAGE + customerId));
    }

    @PostMapping
    public ResponseEntity<CreateUpdateProfileResponse> createBusinessProfile(@RequestBody BusinessProfile businessProfile) {
        BusinessProfileValidator.validateBusinessProfile(businessProfile);
        CreateUpdateProfileResponse createUpdateProfileResponse = businessProfileService.createBusinessProfile(businessProfile);
        return ResponseEntity.accepted().body(createUpdateProfileResponse);
    }

    @PutMapping("/{customerId}")
    @CircuitBreaker(name = "businessProfileFallback", fallbackMethod = "fallbackForUpdatingBusinessProfile")
    public ResponseEntity<CreateUpdateProfileResponse> updateBusinessProfile(
            @PathVariable("customerId") String customerId,
            @RequestBody @Valid BusinessProfile businessProfile) throws ProfileNotFoundException {
        Optional<BusinessProfile> existingProfile = businessProfileService.getBusinessProfileById(customerId);

        if (existingProfile.isPresent()) {
            businessProfile.setCustomerID(customerId);
            CreateUpdateProfileResponse createUpdateProfileResponse = businessProfileService.updateBusinessProfile(businessProfile);
            return ResponseEntity.accepted().body(createUpdateProfileResponse);
        } else {
            throw new ProfileNotFoundException(Constants.PROFILE_NOT_FOUND_MESSAGE + customerId);
        }
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteBusinessProfile(@PathVariable("customerId") String customerId) {
        businessProfileService.deleteBusinessProfile(customerId);
        return ResponseEntity.noContent().build();
    }
}
