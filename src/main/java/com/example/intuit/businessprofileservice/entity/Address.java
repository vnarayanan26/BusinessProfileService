package com.example.intuit.businessprofileservice.entity;

import lombok.Data;


@Data
public class Address {

    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zip;
    private String country;
}
