package com.example.energyconsumption.domain;

import static lombok.AccessLevel.PRIVATE;

import com.example.energyconsumption.domain.Electronic.ElectronicBuilder;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
public class ElectronicFixture {

    public static Electronic build() {
        return builder().build();
    }

    public static Electronic buildWith(
        Long id,
        String name,
        Double power,
        Status status
    ) {
        return builder()
            .id(id)
            .name(name)
            .power(power)
            .status(status)
            .build();
    }

    public static void buildInvalid() {
        Electronic.builder().build();
    }

    private static ElectronicBuilder builder() {

        return Electronic.builder()
            .id(1L)
            .name("Computer")
            .power(15.5)
            .status(Status.ON);
    }
}
