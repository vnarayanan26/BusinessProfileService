package com.example.intuit.businessprofileservice.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressTest {

    @Test
    void testLine1GetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String line1 = "line1";
        addressUnderTest.setLine1(line1);
        assertThat(addressUnderTest.getLine1()).isEqualTo(line1);
    }

    @Test
    void testLine2GetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String line2 = "line2";
        addressUnderTest.setLine2(line2);
        assertThat(addressUnderTest.getLine2()).isEqualTo(line2);
    }

    @Test
    void testCityGetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String city = "city";
        addressUnderTest.setCity(city);
        assertThat(addressUnderTest.getCity()).isEqualTo(city);
    }

    @Test
    void testStateGetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String state = "state";
        addressUnderTest.setState(state);
        assertThat(addressUnderTest.getState()).isEqualTo(state);
    }

    @Test
    void testZipGetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String zip = "zip";
        addressUnderTest.setZip(zip);
        assertThat(addressUnderTest.getZip()).isEqualTo(zip);
    }

    @Test
    void testCountryGetterAndSetter() {
        final Address addressUnderTest = new Address();
        final String country = "country";
        addressUnderTest.setCountry(country);
        assertThat(addressUnderTest.getCountry()).isEqualTo(country);
    }

    @Test
    void testEquals() {
        final Address addressUnderTest = new Address();
        assertThat(addressUnderTest.equals("o")).isFalse();
    }

    @Test
    void testCanEqual() {
        final Address addressUnderTest = new Address();
        assertThat(addressUnderTest.canEqual("other")).isFalse();
    }


    @Test
    void testToString() {
        final Address addressUnderTest = new Address();
        assertThat(addressUnderTest.toString()).isEqualTo("Address(line1=null, line2=null, city=null, state=null, zip=null, country=null)");
    }
}
