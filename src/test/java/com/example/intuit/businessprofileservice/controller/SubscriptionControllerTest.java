package com.example.intuit.businessprofileservice.controller;

import com.example.intuit.businessprofileservice.model.Subscription;
import com.example.intuit.businessprofileservice.service.SubscriptionService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SubscriptionController.class)
class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService mockSubscriptionService;

    @Test
    void testCreateSubscription() throws Exception {
        // Setup
        // Configure SubscriptionService.createSubscription(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        when(mockSubscriptionService.createSubscription(any(Subscription.class))).thenReturn(subscription);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/subscriptions")
                        .content("{}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"customerID\":null,\"id\":null,\"productID\":\"productID\"}");
    }

    @Test
    void testGetSubscriptionById() throws Exception {
        // Setup
        // Configure SubscriptionService.getSubscriptionById(...).
        final Subscription subscription1 = new Subscription();
        subscription1.setProductID("productID");
        final Optional<Subscription> subscription = Optional.of(subscription1);
        when(mockSubscriptionService.getSubscriptionById("id")).thenReturn(subscription);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/subscriptions/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"customerID\":null,\"id\":null,\"productID\":\"productID\"}");
    }

    @Test
    void testGetSubscriptionById_SubscriptionServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockSubscriptionService.getSubscriptionById("id")).thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/subscriptions/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE);
    }

    @Test
    void testGetAllSubscriptions() throws Exception {
        // Setup
        // Configure SubscriptionService.getAllSubscriptions(...).
        final Subscription subscription = new Subscription();
        subscription.setProductID("productID");
        subscription.setCustomerID("customerID");
        final List<Subscription> subscriptions = List.of(subscription);
        when(mockSubscriptionService.getAllSubscriptions()).thenReturn(subscriptions);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/subscriptions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[{\"customerID\":\"customerID\",\"id\":null,\"productID\":\"productID\"}]");
    }

    @Test
    void testGetAllSubscriptions_SubscriptionServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockSubscriptionService.getAllSubscriptions()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/subscriptions")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testUpdateSubscription() throws Exception {
        // Setup
        // Configure SubscriptionService.updateSubscription(...).
        final Subscription subscription1 = new Subscription();
        subscription1.setProductID("productID");
        final Optional<Subscription> subscription = Optional.of(subscription1);
        when(mockSubscriptionService.updateSubscription(eq("id"), any(Subscription.class))).thenReturn(subscription);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/subscriptions/{id}", "id")
                        .content("{}}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"customerID\":null,\"id\":null,\"productID\":\"productID\"}");
    }

    @Test
    void testUpdateSubscription_SubscriptionServiceReturnsAbsent() throws Exception {
        // Setup
        when(mockSubscriptionService.updateSubscription(eq("id"), any(Subscription.class)))
                .thenReturn(Optional.empty());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(put("/subscriptions/{id}", "id")
                        .content("{}").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(Constants.SUBSCRIPTION_NOT_FOUND_MESSAGE);
    }

    @Test
    void testDeleteSubscription() throws Exception {
        // Setup
//        when(mockSubscriptionService.deleteSubscription("id")).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(delete("/subscriptions/{id}", "id")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockSubscriptionService).deleteSubscription("id");
    }
}
