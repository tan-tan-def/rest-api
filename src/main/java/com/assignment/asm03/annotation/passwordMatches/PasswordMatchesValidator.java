package com.assignment.asm03.annotation.passwordMatches;

import com.assignment.asm03.model.UserDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, UserDTO> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context){
        if(userDTO.getPassword()!=null&&userDTO.getConfirmPassword()!=null){
            return userDTO.getPassword().equals(userDTO.getConfirmPassword());
        }
        return false;
    }
//    @Override
//    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext context) {
//        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
//            context.disableDefaultConstraintViolation();
//            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
//                    .addPropertyNode("confirmPassword").addConstraintViolation();
//            return false;
//        }
//        return true;
//    }
}
