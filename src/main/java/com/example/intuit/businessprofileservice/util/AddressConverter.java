package com.example.intuit.businessprofileservice.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.example.intuit.businessprofileservice.entity.Address;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class AddressConverter implements DynamoDBTypeConverter<String, Address> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convert(Address address) {
        try {
            return objectMapper.writeValueAsString(address);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error converting Address to JSON", e);
        }
    }

    @Override
    public Address unconvert(String s) {
        try {
            return objectMapper.readValue(s, Address.class);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error converting JSON to Address", e);
        }
    }
}
