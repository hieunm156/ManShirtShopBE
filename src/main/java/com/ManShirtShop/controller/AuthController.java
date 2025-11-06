package com.ManShirtShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ManShirtShop.dto.auth.LoginRequest;
import com.ManShirtShop.dto.auth.LoginResponse;
import com.ManShirtShop.service.auth.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "api/auth")
@Tag(name = "Authentication API")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "Đăng nhập cho Customer và Employee")
    @PostMapping(value = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = authService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }
}