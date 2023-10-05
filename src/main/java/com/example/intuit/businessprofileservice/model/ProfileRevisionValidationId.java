package com.example.intuit.businessprofileservice.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

public class ProfileRevisionValidationId implements Serializable {

    private static final long serialVersionUID = 2L;

    private String profileValidationId;
    private String validationId;

    @DynamoDBHashKey
    public String getProfileValidationId() {
        return profileValidationId;
    }

    public void setProfileValidationId(String profileId) {
        this.profileValidationId= profileId;
    }

    @DynamoDBRangeKey
    public String getValidationId() {
        return validationId;
    }

    public void setValidationId(String validationId) {
        this.validationId = validationId;
    }

}