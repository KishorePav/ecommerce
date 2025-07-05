package com.usermanagement.usermanagement.controllers;

import com.usermanagement.usermanagement.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.usermanagement.dto.LoginRequest;
import com.usermanagement.usermanagement.dto.RegisterRequest;
import com.usermanagement.usermanagement.security.JwtUtil;
import com.usermanagement.usermanagement.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        userService.register(user);
        return "Registered successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        User user = userService.login(request.getEmail(), request.getPassword());
        return jwtUtil.generateToken(user.getEmail());
    }

    @PostMapping("/forgot-password")
    public String forgotPassword() {
        return "Reset link sent to your email (stub)";
    }

    @PostMapping("/reset-password")
    public String resetPassword() {
        return "Password reset (stub)";
    }

}
