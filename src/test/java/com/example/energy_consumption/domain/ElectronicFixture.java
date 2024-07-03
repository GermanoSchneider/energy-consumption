package com.example.energy_consumption.domain;

import static lombok.AccessLevel.PRIVATE;

import com.example.energy_consumption.domain.Electronic.ElectronicBuilder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ElectronicFixture {

    public static Electronic build() {
        return builder().build();
    }

    public static void buildInvalid() {
        Electronic.builder().build();
    }

    private static ElectronicBuilder builder() {

        return Electronic.builder()
            .id(1L)
            .name("Computer")
            .status(Status.ON);
    }
}
