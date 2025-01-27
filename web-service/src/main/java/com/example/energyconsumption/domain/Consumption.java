package com.example.energyconsumption.domain;

import static com.example.energyconsumption.domain.ConstraintValidator.validate;
import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
public class Consumption {

    Long id;

    @NotNull(message = "should not be null")
    LocalDateTime initialTime;

    @NonFinal
    LocalDateTime endTime;

    @NotNull(message = "should not be null")
    Electronic electronic;

    public BigDecimal getKilowatts() {

        Long totalSeconds = getTotalSeconds();

        BigDecimal calc = new BigDecimal(electronic.getPowerWatts() * totalSeconds / 3600.0);

        return calc.setScale(5, BigDecimal.ROUND_HALF_UP);
    }

    public Long getTotalSeconds() {
        return ChronoUnit.SECONDS.between(
            initialTime,
            Objects.isNull(endTime) ? LocalDateTime.now() : endTime);
    }

    public static class ConsumptionBuilder {

        public Consumption build() {

            var consumption = new Consumption(
                this.id,
                this.initialTime,
                this.endTime,
                this.electronic
            );

            validate(consumption);

            return consumption;
        }
    }
}
