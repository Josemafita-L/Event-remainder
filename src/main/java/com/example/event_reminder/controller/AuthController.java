package com.example.event_reminder.controller;

import com.example.event_reminder.dto.LoginRequest;
import com.example.event_reminder.dto.LoginResponse;
import com.example.event_reminder.model.User;
import com.example.event_reminder.repository.UserRepository;
import com.example.event_reminder.security.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // =========================================================
    // POST 1 - REGISTER
    // =========================================================

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {

        // Check whether username already exists
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists");
        }

        // Check whether email already exists
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Email already exists");
        }

        // Hash the password before storing it
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user into H2 database
        User savedUser = userRepository.save(user);

        // Never return the hashed password
        savedUser.setPassword(null);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }


    // =========================================================
    // POST 2 - LOGIN
    // =========================================================

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        // Find user by username
        User user = userRepository
                .findByUsername(request.getUsername())
                .orElse(null);

        // User does not exist
        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        // Compare entered password with hashed password
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        // Wrong password
        if (!passwordMatches) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(user.getUsername());

        // Return token
        LoginResponse response = new LoginResponse(
                "Login successful",
                token
        );

        return ResponseEntity.ok(response);
    }


    // =========================================================
    // GET - TEST API
    // =========================================================

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        return ResponseEntity.ok(
                "Authentication API is working!"
        );
    }
}