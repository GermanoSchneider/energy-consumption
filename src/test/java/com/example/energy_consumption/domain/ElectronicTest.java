package com.example.energy_consumption.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ElectronicTest {

    @Test
    @DisplayName("validate electronic model")
    void shouldCreateElectronicModel() {
        assertDoesNotThrow(ElectronicFixture::build);
    }

    @Test
    @DisplayName("validate electronic model when it fails")
    void shouldFailWhenTryingToCreateElectronicModel() {

        var exception = assertThrows(
            ConstraintViolationException.class,
            ElectronicFixture::buildInvalid
        );

        String expectedMessage = "name: should not be blank, status: should not be null";

        assertEquals(expectedMessage, exception.getMessage());
    }
}
