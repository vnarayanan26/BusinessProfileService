package com.example.intuit.businessprofileservice.validator;


import com.example.intuit.businessprofileservice.exception.InvalidProfileRequestException;
import com.example.intuit.businessprofileservice.model.BusinessProfile;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;

@UtilityClass
public class BusinessProfileValidator {

    public void validateBusinessProfile(BusinessProfile businessProfile) {
        Validator.validator()
                .isNotNull(businessProfile, "Body cannot be null")
                .isNotNullAndBlank(businessProfile.getCompanyName(), "Company name cannot be empty")
                .isNotNullAndBlank(businessProfile.getEmail(), "Email cannot be empty")
                .isEmail(businessProfile.getEmail(), "Invalid Email")
                .isNotNullOrEmpty(businessProfile.getLegalName(), "Company's legal name cannot be empty")
                .validate(errors -> {
                    throw new InvalidProfileRequestException(StringUtils.join(errors, ","));
                });

    }

}