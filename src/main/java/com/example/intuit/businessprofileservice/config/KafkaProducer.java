package com.example.intuit.businessprofileservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@Slf4j
public class KafkaProducer {


    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void send(String topicName, Object msg) {
        try {
            log.info("Received message :: {} to produce to topic :: {}", kafkaTemplate, topicName);
            String stringMessage = objectMapper.writeValueAsString(msg);
            kafkaTemplate.send(topicName, stringMessage);
        } catch (Exception exception) {
            log.error("Unable to produce message due to error :: {}", exception.getMessage());
        }
    }


}
