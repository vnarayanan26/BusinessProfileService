package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.model.Validation;
import com.example.intuit.businessprofileservice.service.ValidationService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ValidationController.class)
class ValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ValidationService mockValidationService;

    @Test
    void testCreateValidationStatus() throws Exception {
        // Setup
        // Configure ValidationStatusService.createValidationStatus(...).
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setRequestID("requestID");
        validation.setTimestamp(0L);
        validation.setValidationType(Constants.ValidationType.CREATE);
        when(mockValidationService.createValidationStatus(any(Validation.class)))
                .thenReturn(validation);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/validation-status/")
                        .content("{}}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        "{\"customerID\":\"customerID\",\"overallValidationStatus\":\"In_Progress\",\"requestID\":\"requestID\",\"timestamp\":0,\"validationType\":\"CREATE\"}");
    }

    @Test
    void testGetValidationStatus() throws Exception {
        // Setup
        // Configure ValidationStatusService.getValidationStatusById(...).
        final Validation validation1 = new Validation();
        validation1.setCustomerID("customerID");
        validation1.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation1.setRequestID("requestID");
        validation1.setTimestamp(0L);
        validation1.setValidationType(Constants.ValidationType.CREATE);
        final Optional<Validation> validationStatus = Optional.of(validation1);
        when(mockValidationService.getValidationStatusById("requestId")).thenReturn(validationStatus);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/validation-status/{requestId}", "requestId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        "{\"customerID\":\"customerID\",\"overallValidationStatus\":\"In_Progress\",\"requestID\":\"requestID\",\"timestamp\":0,\"validationType\":\"CREATE\"}");
    }

    @Test
    void testGetValidationStatus_ValidationStatusServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockValidationService.getValidationStatusById("requestId")).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/validation-status/{requestId}", "requestId")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(Constants.VALIDATION_NOT_FOUND_MESSAGE);
    }

    @Test
    void testGetAllValidationStatuses() throws Exception {
        // Setup
        // Configure ValidationStatusService.getAllValidationStatuses(...).
        final Validation validation = new Validation();
        validation.setCustomerID("customerID");
        validation.setOverallValidationStatus(Constants.ValidationStatus.In_Progress);
        validation.setRequestID("requestID");
        validation.setTimestamp(0L);
        validation.setValidationType(Constants.ValidationType.CREATE);
        final List<Validation> validations = List.of(validation);
        when(mockValidationService.getAllValidationStatuses()).thenReturn(validations);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/validation-status/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString())
                .isEqualTo(
                        "[{\"customerID\":\"customerID\",\"overallValidationStatus\":\"In_Progress\",\"requestID\":\"requestID\",\"timestamp\":0,\"validationType\":\"CREATE\"}]");
    }

    @Test
    void testGetAllValidationStatuses_ValidationStatusServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockValidationService.getAllValidationStatuses()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/validation-status/")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}
