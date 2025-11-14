package com.UMLStudio.backend.security;

public interface PasswordServicePort {
    String hashPassword(String plainPassword);
    boolean verifyPassword(String plainPassword, String storedHash);
}

