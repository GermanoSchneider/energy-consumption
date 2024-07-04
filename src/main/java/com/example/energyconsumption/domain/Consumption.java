package com.example.energyconsumption.domain;

import static com.example.energyconsumption.domain.ConstraintValidator.validate;
import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = PRIVATE)
public class Consumption {

    @NotNull(message = "should not be null")
    Long id;

    @NotNull(message = "should not be null")
    Double kilowatts;

    @NotNull(message = "should not be null")
    LocalDateTime initialTime;

    @NonFinal
    LocalDateTime endTime;

    @NotNull(message = "should not be null")
    Electronic electronic;

    public Double getKilowatts() {

        return electronic.getPowerKilowatts() * (getTotalMinutes() / 60);
    }

    private Long getTotalMinutes() {
        return ChronoUnit.MINUTES.between(initialTime, endTime);
    }

    public static class ConsumptionBuilder {

        public Consumption build() {

            var consumption = new Consumption(
                this.id,
                this.kilowatts,
                this.initialTime,
                this.endTime,
                this.electronic
            );

            validate(consumption);

            return consumption;
        }
    }
}
