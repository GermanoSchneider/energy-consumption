package com.example.energyconsumption.domain;

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
            "powerWatts: should not be null",
            "id: should not be null",
            "name: should not be blank",
            "status: should not be null"
        };

        assertThat(messages).hasSameElementsAs(stream(expectedMessages).toList());
    }

    @Test
    @DisplayName("validate when the electronic is on")
    void shouldBeOn() {

       Electronic electronic = ElectronicFixture.build();
       assertThat(electronic.isOn()).isFalse();
    }

    @Test
    @DisplayName("should convert the power watts to kilowatts")
    void shouldReturnPowerInKilowatts() {

        double powerWatts = 20.0;

        Electronic electronic = ElectronicFixture
            .build()
            .toBuilder()
            .powerWatts(powerWatts)
            .build();

        double expectedKilowatts = 0.02;

        assertThat(electronic.getPowerKilowatts()).isEqualTo(expectedKilowatts);
    }
}
