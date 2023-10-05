package com.example.intuit.businessprofileservice.listener;

import com.example.intuit.businessprofileservice.entity.ProfileRevisionValidationMessage;
import com.example.intuit.businessprofileservice.service.ProfileRevisionValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProfileRevisionValidationListener {

    @Autowired
    ProfileRevisionValidationService profileRevisionValidationService;

    @KafkaListener(
            topics = "${profile.validation.topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listenForRevisionValidation(String rawMessage) throws Exception {
        ProfileRevisionValidationMessage message = null;
        try {
            message = new ObjectMapper().readValue(rawMessage,
                    ProfileRevisionValidationMessage.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        profileRevisionValidationService.validate(message);
    }

}
