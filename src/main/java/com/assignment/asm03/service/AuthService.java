package com.assignment.asm03.service;

import com.assignment.asm03.model.LoginResponse;

public interface AuthService {
    LoginResponse attemptLogin(String email, String password);
}
