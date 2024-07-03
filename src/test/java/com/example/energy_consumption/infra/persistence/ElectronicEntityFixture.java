package com.example.energy_consumption.infra.persistence;

import static lombok.AccessLevel.PRIVATE;

import com.example.energy_consumption.domain.Status;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
class ElectronicEntityFixture {

    static ElectronicEntity buildWith(
        Long id,
        String name,
        Double power,
        Status status
    ) {

        return new ElectronicEntity(id, name, power, status);
    }
}
