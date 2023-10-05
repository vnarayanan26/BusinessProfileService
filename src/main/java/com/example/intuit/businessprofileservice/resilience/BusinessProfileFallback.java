package com.example.intuit.businessprofileservice.resilience;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BusinessProfileFallback {

    public ResponseEntity<?> fallbackForGettingBusinessProfile(String businessProfileId,
                                                               Throwable throwable) throws Exception {
        throw new Exception("System not available. Please try again in some time");
    }

    public ResponseEntity<?> fallbackForUpdatingBusinessProfile() throws Exception {
        throw new Exception("System not available. Please try again in some time");
    }
}