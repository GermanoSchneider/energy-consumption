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
    @DisplayName("calculate the energy consumption without end time value")
    void shouldCalculateTheEnergyConsumptionWithoutEndTime() {

        double powerWatts = 15.5;

        Electronic electronic  = ElectronicFixture.build()
            .toBuilder()
            .powerWatts(powerWatts)
            .build();

        LocalDateTime initialTime = LocalDateTime.now().minusSeconds(40);

        Long totalSeconds = getTotalSeconds(initialTime, LocalDateTime.now());

        BigDecimal expectedKilowatts = new BigDecimal(powerWatts * totalSeconds / 3600.0)
            .setScale(5,
            BigDecimal.ROUND_HALF_UP);

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .initialTime(initialTime)
            .endTime(null)
            .electronic(electronic)
            .build();

        assertThat(consumption.getKilowatts())
            .isEqualTo(expectedKilowatts);
    }

    @Test
    @DisplayName("calculate the energy consumption with end time value")
    void shouldCalculateTheEnergyConsumptionWithEndTime() {

        double powerWatts = 15.5;

        Electronic electronic  = ElectronicFixture.build()
            .toBuilder()
            .powerWatts(powerWatts)
            .build();

        LocalDateTime initialTime = LocalDateTime.now();
        LocalDateTime endTime = initialTime.plusHours(2);

        Long totalSeconds = getTotalSeconds(initialTime, endTime);

        BigDecimal expectedKilowatts = new BigDecimal(powerWatts * totalSeconds / 3600.0)
            .setScale(5,
                BigDecimal.ROUND_HALF_UP);

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .initialTime(initialTime)
            .endTime(endTime)
            .electronic(electronic)
            .build();

        assertThat(consumption.getKilowatts())
            .isEqualTo(expectedKilowatts);
    }

    @Test
    @DisplayName("calculate the seconds between initial and end time")
    void shouldCalculateSecondsBetweenInitialAndEndTime() {

        LocalDateTime initialTime = LocalDateTime.now();
        LocalDateTime endTime = initialTime.plusHours(2);

        Long expectedResult = getTotalSeconds(initialTime, endTime);

        Consumption consumption = ConsumptionFixture.build()
            .toBuilder()
            .initialTime(initialTime)
            .endTime(endTime)
            .build();

        assertThat(expectedResult)
            .isEqualTo(consumption.getTotalSeconds());
    }

    private Long getTotalSeconds(LocalDateTime initialTime, LocalDateTime endTime) {
        return ChronoUnit.SECONDS.between(initialTime, endTime);
    }
}