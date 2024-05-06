package io.barth.sms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumberValidation.class)
public @interface NumberCheck {
    String message() default "Quantity can not be null or less than 1";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
