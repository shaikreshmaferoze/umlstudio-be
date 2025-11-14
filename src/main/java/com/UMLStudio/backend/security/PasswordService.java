package com.UMLStudio.backend.security;

import org.springframework.stereotype.Component;

@Component
public class PasswordService implements PasswordServicePort {

    @Override
    public String hashPassword(String plainPassword) {
        return PasswordUtils.generateStrongPasswordHash(plainPassword);
    }

    @Override
    public boolean verifyPassword(String plainPassword, String storedHash) {
        return PasswordUtils.verifyPassword(plainPassword, storedHash);
    }
}

