package com.example.Skool.common.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = ListOfUrlsValidator.class)
public @interface ListOfUrlsConstraint {
    String message() default "must be valid urls";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
