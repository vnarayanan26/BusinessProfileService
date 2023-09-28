package com.example.intuit.businessprofileservice.util;

import com.example.intuit.businessprofileservice.entity.Address;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddressConverterTest {

    @Test
    void testConvert() {
        // Setup
        final AddressConverter addressConverterUnderTest = new AddressConverter();
        final Address address = new Address();
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCity("city");
        address.setState("state");
        address.setZip("zip");

        // Run the test
        final String result = addressConverterUnderTest.convert(address);

        // Verify the results
        assertThat(result).isEqualTo("{\"line1\":\"line1\",\"line2\":\"line2\",\"city\":\"city\",\"state\":\"state\",\"zip\":\"zip\",\"country\":null}");
    }

    @Test
    void testUnconvert() {
        // Setup
        final AddressConverter addressConverterUnderTest = new AddressConverter();
        final Address expectedResult = new Address();
        expectedResult.setLine1("line1");
        expectedResult.setLine2("line2");
        expectedResult.setCity("city");
        expectedResult.setState("state");
        expectedResult.setZip("zip");

        // Run the test
        final Address result = addressConverterUnderTest.unconvert("{\"line1\":\"line1\",\"line2\":\"line2\",\"city\":\"city\",\"state\":\"state\",\"zip\":\"zip\",\"country\":null}");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
