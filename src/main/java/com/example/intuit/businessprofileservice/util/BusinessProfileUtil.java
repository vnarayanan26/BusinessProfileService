package com.example.intuit.businessprofileservice.util;

import com.example.intuit.businessprofileservice.model.BusinessProfile;

public final class BusinessProfileUtil {

    public static void updateBusinessProfileAttributes(BusinessProfile existingProfile, BusinessProfile newProfile) {
        // Update individual attributes of the existing profile with the new profile
        existingProfile.setCompanyName(newProfile.getCompanyName());
        existingProfile.setLegalName(newProfile.getLegalName());
        existingProfile.setBusinessAddress(newProfile.getBusinessAddress());
        existingProfile.setLegalAddress(newProfile.getLegalAddress());
        existingProfile.setTaxIdentifiers(newProfile.getTaxIdentifiers());
        existingProfile.setEmail(newProfile.getEmail());
        existingProfile.setWebsite(newProfile.getWebsite());
        // Add more attribute updates as needed
    }

    public static boolean validateSubscription(String product) {
        // Call the validate function for the subscription
        // You'll need to implement the validation logic based on your use case
        // For demonstration purposes, we'll just print the validation message
        return true;
    }
}
