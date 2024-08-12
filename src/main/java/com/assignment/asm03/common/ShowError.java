package com.assignment.asm03.common;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ShowError {
    public static String showError(BindingResult result){
        String errorMessage = "";
        for(FieldError error : result.getFieldErrors()) {
            errorMessage += error.getField() + ": " + error.getDefaultMessage() + ". \n";
        }
        for (ObjectError error : result.getGlobalErrors()) {
            errorMessage += error.getDefaultMessage() + ". \n";
        }
        return errorMessage;
    }
}
