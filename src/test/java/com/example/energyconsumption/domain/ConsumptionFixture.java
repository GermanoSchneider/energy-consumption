package com.example.energyconsumption.domain;

import static lombok.AccessLevel.PRIVATE;

import com.example.energyconsumption.domain.Consumption.ConsumptionBuilder;
import java.time.LocalDateTime;
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

        LocalDateTime currentTime = LocalDateTime.now();

        return Consumption.builder()
            .id(1L)
            .kilowatts(15.2)
            .initialTime(currentTime)
            .endTime(currentTime.plusHours(1))
            .electronic(ElectronicFixture.build());
    }
}
