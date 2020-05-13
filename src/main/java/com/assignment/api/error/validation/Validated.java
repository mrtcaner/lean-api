package com.assignment.api.error.validation;

import javax.validation.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Validated.Validator.class})
public @interface Validated {

    String message()

            default "Fruit serial number is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Validated, Object> {
        @Override
        public void initialize(final Validated object) {
        }

        @Override
        public boolean isValid(Object obj,
                               ConstraintValidatorContext constraintValidatorContext) {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            javax.validation.Validator validator = factory.getValidator();
            Set<ConstraintViolation<Object>> violations = validator.validate(obj);
            if (!violations.isEmpty()) {
                return false;
            }

            return true;

        }
    }
}