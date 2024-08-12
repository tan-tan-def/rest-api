package com.assignment.asm03.security;

import com.assignment.asm03.exception.JwtDecodeExceptionCustom;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;
    public DecodedJWT decode(String token){
        try {
            return JWT.require(Algorithm.HMAC256(jwtProperties.getSecretKeys()))
                    .build()
                    .verify(token);
        }catch (JWTDecodeException e){
            throw new JwtDecodeExceptionCustom("Token không hợp lệ",e);
        }
    }
}
