package com.example.energyconsumption.domain;

import static java.time.LocalDate.now;
import static lombok.AccessLevel.PRIVATE;

import com.example.energyconsumption.domain.Consumption.ConsumptionBuilder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ConsumptionFixture {

    public static Consumption build() {
        return builder().build();
    }

    public static Consumption buildWith(Electronic electronic) {
        return builder()
            .electronic(electronic)
            .build();
    }

    public static void buildInvalid() {
        Consumption.builder().build();
    }

    private static ConsumptionBuilder builder() {

        return Consumption.builder()
            .id(1L)
            .kilowatts(15.2)
            .date(now())
            .electronic(ElectronicFixture.build());
    }
}
