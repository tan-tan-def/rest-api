package com.assignment.asm03.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.Duration;
@Setter
@Getter
//@Configuration
@Component
@ConfigurationProperties("security.jwt")
public class JwtProperties {
    private String secretKeys;
    private Duration tokenDuration;
}
