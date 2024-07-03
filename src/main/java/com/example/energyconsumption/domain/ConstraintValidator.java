package com.example.energyconsumption.domain;

import static jakarta.validation.Validation.buildDefaultValidatorFactory;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;

class ConstraintValidator {

    private static final Validator VALIDATOR = buildDefaultValidatorFactory().getValidator();

    static <T> void validate(T model) {

        var errors = VALIDATOR.validate(model);

        if (!errors.isEmpty()) {
            throw new ConstraintViolationException(errors);
        }
    }
}
