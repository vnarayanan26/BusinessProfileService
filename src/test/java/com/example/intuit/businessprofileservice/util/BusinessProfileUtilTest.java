package com.example.intuit.businessprofileservice.util;

import com.example.intuit.businessprofileservice.entity.Address;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessProfileUtilTest {

    @Test
    void testUpdateBusinessProfileAttributes() {
        // Setup
        final BusinessProfile existingProfile = new BusinessProfile();
        existingProfile.setCompanyName("companyName");
        existingProfile.setLegalName("legalName");
        existingProfile.setEmail("email");
        existingProfile.setWebsite("website");
        final Address businessAddress = new Address();
        existingProfile.setBusinessAddress(businessAddress);
        final Address legalAddress = new Address();
        existingProfile.setLegalAddress(legalAddress);
        existingProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        final BusinessProfile newProfile = new BusinessProfile();
        newProfile.setCompanyName("companyName");
        newProfile.setLegalName("legalName");
        newProfile.setEmail("email");
        newProfile.setWebsite("website");
        final Address businessAddress1 = new Address();
        newProfile.setBusinessAddress(businessAddress1);
        final Address legalAddress1 = new Address();
        newProfile.setLegalAddress(legalAddress1);
        newProfile.setTaxIdentifiers(Map.ofEntries(Map.entry("value", "value")));

        // Run the test
        BusinessProfileUtil.updateBusinessProfileAttributes(existingProfile, newProfile);

        // Verify the results
    }

    @Test
    void testValidateSubscription() {
        assertThat(BusinessProfileUtil.validateSubscription("product")).isTrue();
    }
}
