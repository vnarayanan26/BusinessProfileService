package com.example.intuit.businessprofileservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class Properties {

    @Value("${amazon.aws.accesskey}")
    private String amazonAWSAccessKey;

    @Value("${amazon.aws.secretkey}")
    private String amazonAWSSecretKey;

    @Value("${amazon.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;

    @Value("${spring.kafka.consumer.group-id}")
    private String consumerGroupId;

    @Value("#{new Boolean('${async.pattern.events}')}")
    private Boolean useMessagingEvents;

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;


    @Value("${profile.validation.topic}")
    private String businessProfileValidationTopic;

}
