package com.assignment.asm03.security;

import com.assignment.asm03.entity.User;
import com.assignment.asm03.service.UserService;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class JwtToPrincipalConverter {
    private final UserService userService;

    public JwtToPrincipalConverter(UserService userService) {
        this.userService = userService;
    }

    public CustomUserDetail convert(DecodedJWT jwt){
        User user = userService.findByEmail(jwt.getClaim("email").asString());
        return CustomUserDetail.builder()
                .user(user)
                .authorities(extractAuthoritiesFromClaim(jwt))
                .build();
    }
    private List<SimpleGrantedAuthority> extractAuthoritiesFromClaim(DecodedJWT jwt){
        var claim = jwt.getClaim("roles");

        if(claim.isMissing() || claim.isNull()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
