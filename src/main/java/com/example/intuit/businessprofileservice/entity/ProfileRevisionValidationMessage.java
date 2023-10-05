package com.example.intuit.businessprofileservice.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRevisionValidationMessage {

    private String revisionId;

    private String productId;

    private String profileValidationId;

    // Temp Argument
    private boolean toAccept;
}
