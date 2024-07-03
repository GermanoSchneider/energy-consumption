package com.example.energy_consumption.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ConsumptionTest {

    @Test
    @DisplayName("validate consumption model")
    void shouldCreateConsumptionModel() {
        assertDoesNotThrow(ConsumptionFixture::build);
    }

    @Test
    @DisplayName("validate consumption model when it fails")
    void shouldFailWhenTryingToCreateConsumptionModel() {

        var exception = assertThrows(
            ConstraintViolationException.class,
            ConsumptionFixture::buildInvalid
        );

        String expectedMessage = "electronic: should not be null, "
            + "kilowatts: should not be null, "
            + "id: should not be null, "
            + "date: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }
}