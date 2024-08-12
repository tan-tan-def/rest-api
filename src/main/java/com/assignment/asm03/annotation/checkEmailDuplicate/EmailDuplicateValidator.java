package com.assignment.asm03.annotation.checkEmailDuplicate;

import com.assignment.asm03.entity.User;
import com.assignment.asm03.service.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class EmailDuplicateValidator implements ConstraintValidator<EmailNotDuplicate, String> {
    private final UserService userService;
    @Override
    public void initialize(EmailNotDuplicate constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        User userCheck = userService.findByEmailForAnnotation(value);

        return userCheck==null;
    }

}
