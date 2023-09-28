package com.example.intuit.businessprofileservice.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.example.intuit.businessprofileservice.util.Constants;

@DynamoDBTable(tableName = "ValidationStatus")
public class Validation {

    @DynamoDBHashKey(attributeName = "RequestID")
    private String requestID;

    private String customerID;
    private Constants.ValidationStatus overallValidationStatus;

    private Constants.ValidationType validationType;
    private long timestamp;

    @DynamoDBAttribute(attributeName = "CustomerID")
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @DynamoDBAttribute(attributeName = "OverallValidationStatus")
    @DynamoDBTypeConvertedEnum
    public Constants.ValidationStatus getOverallValidationStatus() {
        return overallValidationStatus;
    }

    public void setOverallValidationStatus(Constants.ValidationStatus overallValidationStatus) {
        this.overallValidationStatus = overallValidationStatus;
    }


    @DynamoDBAttribute(attributeName = "ValidationType")
    @DynamoDBTypeConvertedEnum
    public Constants.ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(Constants.ValidationType validationType) {
        this.validationType = validationType;
    }


    @DynamoDBHashKey(attributeName = "RequestID")
    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    @DynamoDBAttribute(attributeName = "Timestamp")
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
