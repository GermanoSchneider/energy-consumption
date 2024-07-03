package com.example.energyconsumption.infra.persistence.consumption;

import static java.time.LocalDate.now;
import static lombok.AccessLevel.PRIVATE;

import com.example.energyconsumption.infra.persistence.electronic.ElectronicEntityFixture;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class ConsumptionEntityFixture {

    static ConsumptionEntity build() {

        return new ConsumptionEntity(
            null,
            30.5,
            now(),
            ElectronicEntityFixture.build()
        );
    }
}
