package com.example.intuit.businessprofileservice.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.intuit.businessprofileservice.entity.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class TaxIdentifierConverter implements DynamoDBTypeConverter<String, Map<String, String>> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(Map<String, String> taxIdentifiers) {
        try {
            return objectMapper.writeValueAsString(taxIdentifiers);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting taxIdentifiers to JSON", e);
        }
    }

    @Override
    public Map<String, String> unconvert(String s) {
        try {
            return objectMapper.readValue(s, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to taxIdentifiers", e);
        }
    }
}


