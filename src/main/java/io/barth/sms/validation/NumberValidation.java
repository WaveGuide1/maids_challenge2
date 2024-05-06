package io.barth.sms.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumberValidation implements ConstraintValidator<NumberCheck, Integer> {
    @Override
    public void initialize(NumberCheck constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        if(integer == null)
            return false;
        return integer > 0;
    }
}
