package com.example.energy_consumption.domain;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        String[] messages = exception.getMessage().split(", ");

        String[] expectedMessages = {
            "electronic: should not be null",
            "kilowatts: should not be null",
            "id: should not be null",
            "date: should not be null"
        };

        assertThat(messages).hasSameElementsAs(stream(expectedMessages).toList());
    }
}