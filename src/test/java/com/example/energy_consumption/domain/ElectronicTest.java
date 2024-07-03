package com.example.energy_consumption.domain;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
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

        String[] messages = exception.getMessage().split(", ");

        String[] expectedMessages = {
            "power: should not be null",
            "id: should not be null",
            "name: should not be blank",
            "status: should not be null"
        };

        assertThat(messages).hasSameElementsAs(stream(expectedMessages).toList());
    }
}
