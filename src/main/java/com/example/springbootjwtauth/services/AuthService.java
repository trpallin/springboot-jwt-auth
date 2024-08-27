package com.example.springbootjwtauth.services;

import com.example.springbootjwtauth.dtos.LoginRequest;
import com.example.springbootjwtauth.dtos.SignUpRequest;
import com.example.springbootjwtauth.models.User;
import com.example.springbootjwtauth.repositories.UserRepository;
import com.example.springbootjwtauth.utils.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUpUser(SignUpRequest signUpRequest) {
        if (userRepository.findByEmail(signUpRequest.getEmail()) != null) {
            throw new IllegalArgumentException("The email already exists");
        }
        String encodedPassword = passwordEncoder.encode(signUpRequest.getPassword());
        User user = new User(signUpRequest.getEmail(), encodedPassword);
        userRepository.addUser(user);
        return user;
    }

    public String authenticateUser(LoginRequest loginRequest) {
        User existingUser = userRepository.findByEmail(loginRequest.getEmail());
        if (existingUser != null && isPasswordValid(loginRequest, existingUser)) {
            return jwtUtil.generateToken(loginRequest.getEmail());
        } else {
            throw new IllegalArgumentException("Invalid email or password");
        }
    }

    private boolean isPasswordValid(LoginRequest loginRequest, User existingUser) {
        return passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword());
    }
}
