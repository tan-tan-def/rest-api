package com.assignment.asm03.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtIssuer {
    private final JwtProperties jwtProperties;
    public String issuer(int userId, String email, List<String> roles){
        var now = Instant.now();
        return
                JWT.create()
                        .withSubject(String.valueOf(userId))
                        .withIssuedAt(now)
                        .withExpiresAt(now.plus(jwtProperties.getTokenDuration()))
                        .withClaim("email", email)
                        .withClaim("roles", roles)
                        .sign(Algorithm.HMAC256(jwtProperties.getSecretKeys()));
    }
}
