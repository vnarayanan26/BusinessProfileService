package com.example.intuit.businessprofileservice.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.intuit.businessprofileservice.util.Constants;
import org.springframework.data.annotation.Id;

@DynamoDBTable(tableName = "ProfileRevisionValidation")
public class ProfileRevisionValidation {

    @Id
    private ProfileRevisionValidationId profileRevisionValidationId;


    @DynamoDBHashKey(attributeName = "ProfileValidationId")
    public String getProfileValidationId() {
        return profileRevisionValidationId != null ? profileRevisionValidationId.getProfileValidationId() : null;
    }

    public void setProfileValidationId(String profileId) {
        if (profileRevisionValidationId == null) {
            profileRevisionValidationId = new ProfileRevisionValidationId();
        }
        profileRevisionValidationId.setProfileValidationId(profileId);
    }


    @DynamoDBRangeKey(attributeName = "ValidationId")
    public String getValidationId() {
        return profileRevisionValidationId != null ? profileRevisionValidationId.getValidationId() : null;
    }

    public void setValidationId(String validationId) {
        if (profileRevisionValidationId == null) {
            profileRevisionValidationId = new ProfileRevisionValidationId();
        }
        profileRevisionValidationId.setValidationId(validationId);
    }

    @DynamoDBAttribute(attributeName = "ProductId")
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @DynamoDBAttribute(attributeName = "ValidationStatus")
    @DynamoDBTypeConvertedEnum
    public Constants.ValidationStatus getStatus() {
        return status;
    }

    public void setStatus(Constants.ValidationStatus status) {
        this.status = status;
    }

    @DynamoDBAttribute(attributeName = "Timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    private String productId;

    private Constants.ValidationStatus status;

    private long timestamp;
}
