package com.example.energyconsumption.domain;

import static java.util.Arrays.stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
            "initialTime: should not be null"
        };

        assertThat(messages).hasSameElementsAs(stream(expectedMessages).toList());
    }

    @Test
    @DisplayName("calculate the energy consumption")
    void shouldCalculateTheEnergyConsumption() {

        double powerWatts = 15.5;
        double powerKilowatts = powerWatts / 1000.0;

        Electronic electronic  = ElectronicFixture.build()
            .toBuilder()
            .powerWatts(powerWatts)
            .build();

        LocalDateTime initialTime = LocalDateTime.now().minusSeconds(40);

        Long totalSeconds = getTotalMinutes(initialTime, LocalDateTime.now());

        BigDecimal expectedKilowatts = new BigDecimal(powerKilowatts * totalSeconds / 3600.0)
            .setScale(4,
            BigDecimal.ROUND_HALF_UP);

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .initialTime(initialTime)
            .electronic(electronic)
            .build();

        assertThat(consumption.getKilowatts())
            .isEqualTo(expectedKilowatts);
    }

    private Long getTotalMinutes(LocalDateTime initialTime, LocalDateTime endTime) {
        return ChronoUnit.SECONDS.between(initialTime, endTime);
    }
}