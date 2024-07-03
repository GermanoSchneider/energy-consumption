package com.example.energyconsumption.domain;

import static com.example.energyconsumption.domain.ConstraintValidator.validate;
import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Consumption {

    @NotNull(message = "should not be null")
    Long id;

    @NotNull(message = "should not be null")
    Double kilowatts;

    @NotNull(message = "should not be null")
    LocalDate date;

    @NotNull(message = "should not be null")
    Electronic electronic;

    public static class ConsumptionBuilder {

        public Consumption build() {

            var consumption = new Consumption(
                this.id,
                this.kilowatts,
                this.date,
                this.electronic
            );

            validate(consumption);

            return consumption;
        }
    }
}