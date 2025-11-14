package com.UMLStudio.backend.security;

public interface JwtServicePort {
    String generateToken(String username, Long userId);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
    Long getUserIdFromToken(String token);
}

