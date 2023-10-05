package com.example.intuit.businessprofileservice.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.intuit.businessprofileservice.entity.Address;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class BusinessProfileConverter implements DynamoDBTypeConverter<String, BusinessProfile> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(BusinessProfile businessProfile) {
        try {
            return objectMapper.writeValueAsString(businessProfile);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Address to JSON", e);
        }
    }

    @Override
    public BusinessProfile unconvert(String s) {
        try {
            return objectMapper.readValue(s, BusinessProfile.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to Address", e);
        }
    }
}
