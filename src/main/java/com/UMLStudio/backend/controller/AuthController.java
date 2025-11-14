package com.UMLStudio.backend.controller;

import com.UMLStudio.backend.dto.LoginRequest;
import com.UMLStudio.backend.dto.RegisterRequest;
import com.UMLStudio.backend.security.JwtServicePort;
import com.UMLStudio.backend.service.interfaces.AuthServicePort;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(originPatterns = "*")
public class AuthController {

    private final AuthServicePort authService;
    private final JwtServicePort jwtService;

    public AuthController(AuthServicePort authService, JwtServicePort jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    // @GetMapping("/isUserLoggedIn")
    // public ResponseEntity<Map<String, Object>> isUserLoggedIn(@RequestHeader(name = "Authorization", required = false) String authHeader) {
    //     Map<String, Object> body = new HashMap<>();
    //     if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
    //         body.put("message", "No token provided");
    //         body.put("status", "FAILED");
    //         body.put("loggedIn", false);
    //         return ResponseEntity.status(401).body(body);
    //     }

    //     String token = authHeader.substring(7);
    //     boolean valid = jwtService.validateToken(token);
    //     if (!valid) {
    //         body.put("message", "Invalid token");
    //         body.put("status", "FAILED");
    //         body.put("loggedIn", false);
    //         return ResponseEntity.status(401).body(body);
    //     }

    //     Long userId = jwtService.getUserIdFromToken(token);
    //     body.put("message", "Token valid");
    //     body.put("status", "SUCCESS");
    //     body.put("loggedIn", true);
    //     body.put("userId", userId);
    //     return ResponseEntity.ok(body);
    // }
}
