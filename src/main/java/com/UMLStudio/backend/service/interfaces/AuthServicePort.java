package com.UMLStudio.backend.service.interfaces;

import com.UMLStudio.backend.dto.LoginRequest;
import com.UMLStudio.backend.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthServicePort {
    ResponseEntity<Map<String, Object>> register(RegisterRequest request);
    ResponseEntity<Map<String, Object>> login(LoginRequest request);
}

