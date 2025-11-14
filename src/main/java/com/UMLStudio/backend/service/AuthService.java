package com.UMLStudio.backend.service;

import com.UMLStudio.backend.dto.LoginRequest;
import com.UMLStudio.backend.dto.RegisterRequest;
import com.UMLStudio.backend.model.User;
import com.UMLStudio.backend.repository.interfaces.UserRepositoryPort;
import com.UMLStudio.backend.security.JwtServicePort;
import com.UMLStudio.backend.security.PasswordServicePort;
import com.UMLStudio.backend.service.interfaces.AuthServicePort;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements AuthServicePort {

    private final UserRepositoryPort userRepository;
    private final JwtServicePort jwtService;
    private final PasswordServicePort passwordService;

    // public AuthService(UserRepositoryPort userRepository, PasswordServicePort passwordService, JwtServicePort jwtService) {
    //     this.userRepository = userRepository;
    //     this.passwordService = passwordService;
    //     this.jwtService = jwtService;
    // }

    @Override
    public ResponseEntity<Map<String, Object>> register(RegisterRequest request) {
        Map<String, Object> body = new HashMap<>();

        if (userRepository.existsByUsername(request.getUsername())) {
            body.put("message", "Username already in use");
            body.put("status", "FAILED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }

        // if (userRepository.existsByEmail(request.getEmail())) {
        //     body.put("message", "Email already in use");
        //     body.put("status", "FAILED");
        //     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        // }

        String encoded = passwordService.hashPassword(request.getPassword());
        User user = new User(request.getName(), request.getUsername(), encoded);
        User saved = userRepository.save(user);

        body.put("message", "User registered successfully");
        body.put("status", "SUCCESS");
        body.put("userId", saved.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(body);

    }

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
        Map<String, Object> body = new HashMap<>();

        // allow login with username or email
        Optional<User> isUser=Optional.empty();
        if (request.getUsername() != null && !request.getUsername().isBlank()) {
            isUser = userRepository.findByUsername(request.getUsername());
        }
        // } else if (request.getEmail() != null && !request.getEmail().isBlank()) {
        //     user = userRepository.findByEmail(request.getEmail()).orElse(null);
        // }

        if (isUser.isEmpty()) {
            body.put("message", "Invalid Credentials");
            body.put("status", "FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        User user=isUser.get();

        if (!passwordService.verifyPassword(request.getPassword(), user.getPasswordHash())) {
            body.put("message", "Invalid Credentials");
            body.put("status", "FAILED");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }

        String token = jwtService.generateToken(user.getUsername(), user.getUserId());
        body.put("message", "User logged in successfully");
        body.put("status", "SUCCESS");
        body.put("token", token);
        return ResponseEntity.ok(body);
    }
}
