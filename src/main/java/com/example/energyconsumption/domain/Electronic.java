package com.example.energyconsumption.domain;

import static com.example.energyconsumption.domain.ConstraintValidator.validate;
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

    @NotNull(message = "should not be null")
    Long id;

    @NotBlank(message = "should not be blank")
    String name;

    @NotNull(message = "should not be null")
    Double power;

    @NotNull(message = "should not be null")
    Status status;

    public static class ElectronicBuilder {

        public Electronic build() {

            var electronic = new Electronic(
                this.id,
                this.name,
                this.power,
                this.status
            );

            validate(electronic);

            return electronic;
        }
    }
}
