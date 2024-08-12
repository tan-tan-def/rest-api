package com.assignment.asm03.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserPrincipalAuthenticationToken extends AbstractAuthenticationToken {
    private final CustomUserDetail principal;
    public UserPrincipalAuthenticationToken(CustomUserDetail principal) {
        super(principal.getAuthorities());
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public CustomUserDetail getPrincipal() {
        return principal;
    }
}
