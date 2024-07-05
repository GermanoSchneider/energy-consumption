package com.example.energyconsumption.infra.persistence.consumption;

import static lombok.AccessLevel.PRIVATE;

import com.example.energyconsumption.infra.persistence.electronic.ElectronicEntityFixture;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class ConsumptionEntityFixture {

    static ConsumptionEntity build() {

        LocalDateTime currentTime = LocalDateTime.now()
            .minusSeconds(0)
            .minusNanos(0);

        return new ConsumptionEntity(
            null,
            currentTime,
            currentTime.plusHours(1),
            ElectronicEntityFixture.build()
        );
    }
}
