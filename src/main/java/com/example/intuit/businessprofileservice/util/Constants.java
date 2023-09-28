package com.example.intuit.businessprofileservice.util;

public final class Constants {

    public static final String PROFILE_NOT_FOUND_MESSAGE = "Profile not found for id ";

    public static final String VALIDATION_NOT_FOUND_MESSAGE = "Validation not found for id ";

    public static final String SUBSCRIPTION_NOT_FOUND_MESSAGE = "Subscription not found for id ";

    public static final String NO_SUBSCRIPTIONS_FOUND_MESSAGE = "No subscription found for id ";
    public static final String PROFILE_NOT_VALIDATED_MESSAGE = "Profile not validated for id ";

    private Constants() {

    }

    public enum ValidationStatus {
        In_Progress, Rejected, Accepted
    }

    public enum ValidationType {
        CREATE, UPDATE
    }
}
