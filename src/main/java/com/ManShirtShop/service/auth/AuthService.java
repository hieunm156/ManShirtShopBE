package com.ManShirtShop.service.auth;

import com.ManShirtShop.dto.auth.LoginRequest;
import com.ManShirtShop.dto.auth.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
}