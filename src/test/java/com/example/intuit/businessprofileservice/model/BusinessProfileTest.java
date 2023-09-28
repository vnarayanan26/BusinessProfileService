package com.example.intuit.businessprofileservice.model;

import com.example.intuit.businessprofileservice.entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class BusinessProfileTest {

    @Mock
    private Address mockBusinessAddress;
    @Mock
    private Address mockLegalAddress;
    @Mock
    private Map<String, String> mockTaxIdentifiers;

    private BusinessProfile businessProfileUnderTest;

    @BeforeEach
    void setUp() {
        businessProfileUnderTest = new BusinessProfile();
        businessProfileUnderTest.setBusinessAddress(mockBusinessAddress);
        businessProfileUnderTest.setLegalAddress(mockLegalAddress);
        businessProfileUnderTest.setTaxIdentifiers(mockTaxIdentifiers);
    }

    @Test
    void testCustomerIDGetterAndSetter() {
        final String customerID = "customerID";
        businessProfileUnderTest.setCustomerID(customerID);
        assertThat(businessProfileUnderTest.getCustomerID()).isEqualTo(customerID);
    }

    @Test
    void testCompanyNameGetterAndSetter() {
        final String companyName = "companyName";
        businessProfileUnderTest.setCompanyName(companyName);
        assertThat(businessProfileUnderTest.getCompanyName()).isEqualTo(companyName);
    }

    @Test
    void testLegalNameGetterAndSetter() {
        final String legalName = "legalName";
        businessProfileUnderTest.setLegalName(legalName);
        assertThat(businessProfileUnderTest.getLegalName()).isEqualTo(legalName);
    }

    @Test
    void testEmailGetterAndSetter() {
        final String email = "email";
        businessProfileUnderTest.setEmail(email);
        assertThat(businessProfileUnderTest.getEmail()).isEqualTo(email);
    }

    @Test
    void testWebsiteGetterAndSetter() {
        final String website = "website";
        businessProfileUnderTest.setWebsite(website);
        assertThat(businessProfileUnderTest.getWebsite()).isEqualTo(website);
    }

    @Test
    void testGetBusinessAddress() {
        assertThat(businessProfileUnderTest.getBusinessAddress()).isEqualTo(mockBusinessAddress);
    }

    @Test
    void testGetLegalAddress() {
        assertThat(businessProfileUnderTest.getLegalAddress()).isEqualTo(mockLegalAddress);
    }

    @Test
    void testGetTaxIdentifiers() {
        assertThat(businessProfileUnderTest.getTaxIdentifiers()).isEqualTo(mockTaxIdentifiers);
    }

    @Test
    void testLastUpdateTimestampGetterAndSetter() {
        final long lastUpdateTimestamp = 0L;
        businessProfileUnderTest.setLastUpdateTimestamp(lastUpdateTimestamp);
        assertThat(businessProfileUnderTest.getLastUpdateTimestamp()).isEqualTo(lastUpdateTimestamp);
    }
}
