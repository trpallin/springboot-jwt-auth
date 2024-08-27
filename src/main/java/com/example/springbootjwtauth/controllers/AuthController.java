package com.example.springbootjwtauth.controllers;

import com.example.springbootjwtauth.dtos.LoginRequest;
import com.example.springbootjwtauth.dtos.LoginResponse;
import com.example.springbootjwtauth.dtos.SignUpRequest;
import com.example.springbootjwtauth.models.User;
import com.example.springbootjwtauth.services.AuthService;
import com.example.springbootjwtauth.utils.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    ResponseEntity<User> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUpUser(signUpRequest));
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.authenticateUser(loginRequest);
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setExpiresIn(JwtUtil.getExpirationTime());
        return ResponseEntity.ok(response);
    }
}
