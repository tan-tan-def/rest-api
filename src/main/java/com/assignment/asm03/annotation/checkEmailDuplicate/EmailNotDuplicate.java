package com.assignment.asm03.annotation.checkEmailDuplicate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailDuplicateValidator.class)
@Documented
public @interface EmailNotDuplicate {
    String message() default "Email đã đăng kí rồi, vui lòng dùng email khác";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
