package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.entity.response.CreateUpdateProfileResponse;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.example.intuit.businessprofileservice.service.BusinessProfileService;
import com.example.intuit.businessprofileservice.util.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BusinessProfileController.class)
class BusinessProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusinessProfileService mockBusinessProfileService;

    @Test
    void testGetAllBusinessProfiles() throws Exception {
        // Setup
        // Configure BusinessProfileService.getAllBusinessProfiles(...).
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerId");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");
        final List<BusinessProfile> businessProfiles = List.of(businessProfile);
        when(mockBusinessProfileService.getAllBusinessProfiles()).thenReturn(businessProfiles);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/profile")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"businessAddress\":null,\"companyName\":\"companyName\",\"customerID\":\"customerId\",\"email\":\"email\",\"lastUpdateTimestamp\":0,\"legalAddress\":null,\"legalName\":\"legalName\",\"taxIdentifiers\":null,\"website\":\"website\"}]");
    }

    @Test
    void testGetAllBusinessProfiles_BusinessProfileServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockBusinessProfileService.getAllBusinessProfiles()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/profile")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetBusinessProfileById() throws Exception {
        // Setup
        // Configure BusinessProfileService.getBusinessProfileById(...).
        final BusinessProfile businessProfile1 = new BusinessProfile();
        businessProfile1.setCustomerID("customerId");
        businessProfile1.setCompanyName("companyName");
        businessProfile1.setLegalName("legalName");
        businessProfile1.setEmail("email");
        businessProfile1.setWebsite("website");
        final Optional<BusinessProfile> businessProfile = Optional.of(businessProfile1);
        when(mockBusinessProfileService.getBusinessProfileById("customerId")).thenReturn(businessProfile);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/profile/{customerId}", "customerId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"businessAddress\":null,\"companyName\":\"companyName\",\"customerID\":\"customerId\",\"email\":\"email\",\"lastUpdateTimestamp\":0,\"legalAddress\":null,\"legalName\":\"legalName\",\"taxIdentifiers\":null,\"website\":\"website\"}");
    }

    @Test
    void testGetBusinessProfileById_BusinessProfileServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockBusinessProfileService.getBusinessProfileById("customerId")).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/profile/{customerId}", "customerId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(Constants.PROFILE_NOT_FOUND_MESSAGE + "customerId");
    }

    @Test
    void testCreateBusinessProfile() throws Exception {
        // Setup
        // Configure BusinessProfileService.createBusinessProfile(...).
        final BusinessProfile businessProfile = new BusinessProfile();
        businessProfile.setCustomerID("customerId");
        businessProfile.setCompanyName("companyName");
        businessProfile.setLegalName("legalName");
        businessProfile.setEmail("email");
        businessProfile.setWebsite("website");

        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId(businessProfile.getCustomerID());
        createUpdateProfileResponse.setValidationRequestId("requestId");
        when(mockBusinessProfileService.createBusinessProfile(any(BusinessProfile.class))).thenReturn(createUpdateProfileResponse);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/profile")
                        .content("{\"customerId\": null}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"customerId\":\"customerId\",\"validationRequestId\":\"requestId\"}");
    }

    @Test
    void testUpdateBusinessProfile() throws Exception {
        // Setup
        // Configure BusinessProfileService.getBusinessProfileById(...).
        final BusinessProfile businessProfile1 = new BusinessProfile();
        businessProfile1.setCustomerID("customerId");
        businessProfile1.setCompanyName("companyName");
        businessProfile1.setLegalName("legalName");
        businessProfile1.setEmail("email");
        businessProfile1.setWebsite("website");
        final Optional<BusinessProfile> businessProfile = Optional.of(businessProfile1);
        when(mockBusinessProfileService.getBusinessProfileById("customerId")).thenReturn(businessProfile);

        // Configure BusinessProfileService.updateBusinessProfile(...).
        final BusinessProfile businessProfile2 = new BusinessProfile();
        businessProfile2.setCustomerID("customerId");
        businessProfile2.setCompanyName("companyName");
        businessProfile2.setLegalName("legalName");
        businessProfile2.setEmail("email");
        businessProfile2.setWebsite("website");

        CreateUpdateProfileResponse createUpdateProfileResponse = new CreateUpdateProfileResponse();
        createUpdateProfileResponse.setCustomerId(businessProfile2.getCustomerID());
        createUpdateProfileResponse.setValidationRequestId("requestId");
        when(mockBusinessProfileService.createBusinessProfile(any(BusinessProfile.class))).thenReturn(createUpdateProfileResponse);

        when(mockBusinessProfileService.updateBusinessProfile(any(BusinessProfile.class))).thenReturn(createUpdateProfileResponse);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/profile/{customerId}", "customerId")
                        .content("{\"customerId\": null}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(response.getContentAsString())
                .isEqualTo("{\"customerId\":\"customerId\",\"validationRequestId\":\"requestId\"}");
    }

    @Test
    void testUpdateBusinessProfile_BusinessProfileServiceGetBusinessProfileByIdReturnsAbsent() throws Exception {
        // Setup
        when(mockBusinessProfileService.getBusinessProfileById("customerId")).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/profile/{customerId}", "customerId")
                        .content("{\n" +
                                "    \"companyName\": \"johns2\",\n" +
                                "    \"legalName\": null,\n" +
                                "    \"email\": null,\n" +
                                "    \"website\": null,\n" +
                                "    \"businessAddress\": null,\n" +
                                "    \"legalAddress\": null,\n" +
                                "    \"taxIdentifiers\": null,\n" +
                                "    \"lastUpdateTimestamp\": 0\n" +
                                "}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(Constants.PROFILE_NOT_FOUND_MESSAGE + "customerId");
    }

    @Test
    void testDeleteBusinessProfile() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/profile/{customerId}", "customerId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockBusinessProfileService).deleteBusinessProfile("customerId");
    }
}
