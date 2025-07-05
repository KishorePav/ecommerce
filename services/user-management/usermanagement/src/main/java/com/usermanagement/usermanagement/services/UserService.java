package com.usermanagement.usermanagement.services;

import com.usermanagement.usermanagement.models.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Map<String, User> userStore = new ConcurrentHashMap<>();
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public void register(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userStore.put(user.getEmail(), user);
    }

    public User login(String email, String rawPassword) {
        User user = userStore.get(email);
        System.out.println("Login attempt for email: " + email);
        System.out.println("User exists? " + (user != null));
        if (user != null) {
            System.out.println("Stored (encoded) password: " + user.getPassword());
            System.out.println("Raw password: " + rawPassword);
            System.out.println("Password matches? " + encoder.matches(rawPassword, user.getPassword()));
        }
        if (user != null && encoder.matches(rawPassword, user.getPassword())) {
            return user;
        }
        throw new RuntimeException("Invalid credentials");
    }

    public User getUser(String email) {
        return userStore.get(email);
    }

    public void updateUser(String email, String name) {
        User user = userStore.get(email);
        if (user != null) {
            user.setName(name);
        }
    }

}
