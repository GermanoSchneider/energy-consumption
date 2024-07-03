package com.example.energy_consumption.domain;

import static com.example.energy_consumption.domain.ConstraintValidator.validate;
import static lombok.AccessLevel.PRIVATE;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = PRIVATE)
public class Electronic {

    @NotBlank(message = "should not be blank")
    String name;

    @NotNull(message = "should not be null")
    Status status;

    public static class ElectronicBuilder {

        public Electronic build() {

            var electronic = new Electronic(
                this.name,
                this.status
            );

            validate(electronic);

            return electronic;
        }
    }
}
