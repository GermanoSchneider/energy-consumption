package com.example.energy_consumption.infra.persistence;

import static lombok.AccessLevel.PRIVATE;

import com.example.energy_consumption.domain.Status;
import java.time.LocalDate;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class ConsumptionEntityFixture {

    static ConsumptionEntity buildWith(
        Long id,
        Double kilowatts,
        LocalDate date,
        ElectronicEntity electronic
    ) {

        return new ConsumptionEntity(id, kilowatts, date, electronic);
    }
}
