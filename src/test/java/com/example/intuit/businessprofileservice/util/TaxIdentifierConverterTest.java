package com.example.intuit.businessprofileservice.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class TaxIdentifierConverterTest {

    private TaxIdentifierConverter taxIdentifierConverterUnderTest;

    @BeforeEach
    void setUp() {
        taxIdentifierConverterUnderTest = new TaxIdentifierConverter();
    }

    @Test
    void testConvert() {
        // Setup
        final Map<String, String> taxIdentifiers = Map.ofEntries(Map.entry("value", "value"));

        // Run the test
        final String result = taxIdentifierConverterUnderTest.convert(taxIdentifiers);

        // Verify the results
        assertThat(result).isEqualTo("{\"value\":\"value\"}");
    }

    @Test
    void testUnconvert() {
        // Setup
        final Map<String, String> expectedResult = Map.ofEntries(Map.entry("value", "value"));

        // Run the test
        final Map<String, String> result = taxIdentifierConverterUnderTest.unconvert("{\"value\":\"value\"}");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
