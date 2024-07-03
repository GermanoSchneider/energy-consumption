package com.example.energy_consumption.domain;

import static java.time.LocalDate.*;
import static lombok.AccessLevel.PRIVATE;

import com.example.energy_consumption.domain.Consumption.ConsumptionBuilder;
import com.example.energy_consumption.domain.Electronic.ElectronicBuilder;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ConsumptionFixture {

    public static Consumption build() {
        return builder().build();
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
