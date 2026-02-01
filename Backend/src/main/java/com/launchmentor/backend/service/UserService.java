package com.launchmentor.backend.service;

import com.launchmentor.backend.entity.User;
import com.launchmentor.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    public User registerUser(User user) {

        Optional<User> existing = userRepository.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // ✅ ADMIN LOGIC (VERY IMPORTANT)
        if (user.getEmail().equalsIgnoreCase("admin@launchmentor.com")) {
            user.setRole("ADMIN");
        } else {
            user.setRole("USER");
        }

        // In production: password should be hashed (BCrypt)
        return userRepository.save(user);
    }

    // Simple login method: check email and password
    public User login(String email, String password) {
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isEmpty()) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        User user = opt.get();
        // In production: compare hashed password
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return user;
    }

    // Return all users (useful for admin / testing)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find user by id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Update user basic info
    public User updateUser(Long id, User newData) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setName(newData.getName());
        user.setRole(newData.getRole());
        // do not update email here to keep unique constraint safe; or handle carefully
        return userRepository.save(user);
    }
}
