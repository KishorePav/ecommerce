package com.usermanagement.usermanagement.controllers;

import com.usermanagement.usermanagement.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagement.usermanagement.dto.ProfileResponse;
import com.usermanagement.usermanagement.dto.UpdateProfileRequest;
import com.usermanagement.usermanagement.security.JwtUtil;
import com.usermanagement.usermanagement.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/profile")
    public ProfileResponse getProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        User user = userService.getUser(email);
        ProfileResponse res = new ProfileResponse();
        System.out.printf("Profile Respone", res);
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        return res;
    }

    @PutMapping("/profile")
    public String updateProfile(@RequestBody UpdateProfileRequest update, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String email = jwtUtil.extractEmail(token);
        userService.updateUser(email, update.getName());
        return "Profile updated";
    }

}
