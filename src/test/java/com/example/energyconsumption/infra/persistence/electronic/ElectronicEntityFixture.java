package com.example.energyconsumption.infra.persistence.electronic;

import static com.example.energyconsumption.domain.Status.ON;
import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ElectronicEntityFixture {

    public static ElectronicEntity build() {

        return new ElectronicEntity(
            1L,
            "Computer",
            15.5,
            ON
        );
    }
}
