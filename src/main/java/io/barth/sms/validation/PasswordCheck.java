package io.barth.sms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
public @interface PasswordCheck {

    String message() default "Password must be 8 characters long and combination of uppercase letters, lowercase letters, numbers, special characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
