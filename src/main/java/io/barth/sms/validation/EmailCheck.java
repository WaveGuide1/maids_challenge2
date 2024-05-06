package io.barth.sms.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailVerification.class)
public @interface EmailCheck {

    String message() default "Enter valid email address";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
