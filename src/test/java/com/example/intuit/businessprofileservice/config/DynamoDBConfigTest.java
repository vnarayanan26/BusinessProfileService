package com.example.intuit.businessprofileservice.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

class DynamoDBConfigTest {

    private DynamoDBConfig dynamoDBConfigUnderTest;

    @BeforeEach
    void setUp() {
        dynamoDBConfigUnderTest = new DynamoDBConfig();
        ReflectionTestUtils.setField(dynamoDBConfigUnderTest, "amazonAWSAccessKey", "AKIAYESC2VGQRJWU6ZND");
        ReflectionTestUtils.setField(dynamoDBConfigUnderTest, "amazonAWSSecretKey",
                "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");
        ReflectionTestUtils.setField(dynamoDBConfigUnderTest, "amazonDynamoDBEndpoint", "http://localhost:8000/");
    }

    @Test
    void testAmazonAWSCredentialsProvider() {
        // Setup
        // Run the test
        final AWSCredentialsProvider result = dynamoDBConfigUnderTest.amazonAWSCredentialsProvider();

        // Verify the results
    }

    @Test
    void testAmazonAWSCredentials() {
        // Setup
        // Run the test
        final AWSCredentials result = dynamoDBConfigUnderTest.amazonAWSCredentials();

        // Verify the results
    }

    @Test
    void testDynamoDBMapperConfig() {
        assertThat(dynamoDBConfigUnderTest.dynamoDBMapperConfig()).isEqualTo(DynamoDBMapperConfig.DEFAULT);
    }

    @Test
    void testDynamoDBMapper() {
        // Setup
        final AmazonDynamoDB amazonDynamoDB = null;
        final DynamoDBMapperConfig config = DynamoDBMapperConfig.builder().build();

        // Run the test
        final DynamoDBMapper result = dynamoDBConfigUnderTest.dynamoDBMapper(amazonDynamoDB, config);

        // Verify the results
    }

    @Test
    void testAmazonDynamoDB() {
        // Setup
        // Run the test
        final AmazonDynamoDB result = dynamoDBConfigUnderTest.amazonDynamoDB();

        // Verify the results
    }
}
