package com.assignment.asm03.exception;

import com.auth0.jwt.exceptions.JWTDecodeException;

public class JwtDecodeExceptionCustom extends JWTDecodeException {
    public JwtDecodeExceptionCustom(String message) {
        super(message);
    }

    public JwtDecodeExceptionCustom(String message, Throwable cause) {
        super(message, cause);
    }
}
