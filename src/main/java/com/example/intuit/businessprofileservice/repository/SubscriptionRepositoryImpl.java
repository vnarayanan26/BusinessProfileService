//package com.example.intuit.businessprofileservice.repository;
//
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.model.AttributeValue;
//import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
//import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
//import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.*;
//
//@Component
//@Repository
//public abstract class SubscriptionRepositoryImpl implements SubscriptionRepository{
//
//    @Autowired
//    AmazonDynamoDB amazonDynamoDB;
//
//    public Set<String> findAllByCustomerID(String customerId)
//    {
//        Map<String, KeysAndAttributes> requestItems = new HashMap<>();
//        KeysAndAttributes keysAndAttributes = new KeysAndAttributes()
//                .withKeys(Collections.singletonMap("customerID", new AttributeValue(customerId)));
//        requestItems.put("Subscription", keysAndAttributes);
//
//        BatchGetItemRequest batchGetItemRequest = new BatchGetItemRequest()
//                .withRequestItems(requestItems);
//
//        Set<String> productIDs = new HashSet<>();
//
//        BatchGetItemResult batchGetItemResult = amazonDynamoDB.batchGetItem(batchGetItemRequest);
//
//        // Iterate through the items from the BatchGetItem response
//        for (Map<String, AttributeValue> item : batchGetItemResult.getResponses().get("Subscription")) {
//            AttributeValue productIDAttr = item.get("productID");
//            if (productIDAttr != null && productIDAttr.getS() != null) {
//                productIDs.add(productIDAttr.getS());
//            }
//        }
//
//        return productIDs;
//    }
//}
//
//
