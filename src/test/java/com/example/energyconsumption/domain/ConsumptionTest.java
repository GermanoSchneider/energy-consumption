package com.example.energyconsumption.domain;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
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
            "id: should not be null",
            "initialTime: should not be null",
            "kilowatts: should not be null"
        };

        assertThat(messages).hasSameElementsAs(stream(expectedMessages).toList());
    }

    @Test
    @DisplayName("calculate the energy consumption")
    void shouldCalculateTheEnergyConsumption() {

        double powerWatts = 20.0;
        double powerKilowatts = powerWatts / 1000.0;
        int hours = 9;

        Double expectedKilowatts = powerKilowatts * hours;

        Electronic electronic  = ElectronicFixture.build().toBuilder()
            .powerWatts(powerWatts)
            .build();

        LocalDateTime initialTime = LocalDateTime.now()
            .withHour(9)
            .withMinute(10);

        LocalDateTime endTime = LocalDateTime.now()
            .withHour(18)
            .withMinute(30);

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .initialTime(initialTime)
            .endTime(endTime)
            .electronic(electronic)
            .build();

        assertThat(consumption.getKilowatts())
            .isEqualTo(expectedKilowatts);
    }
}