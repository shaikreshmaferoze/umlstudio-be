package com.UMLStudio.backend.repository;

import com.UMLStudio.backend.model.User;
import com.UMLStudio.backend.repository.interfaces.UserRepositoryPort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,UserRepositoryPort {
    // Optional<User> findByUsername(String username);
    // boolean existsByUsername(String username);
}

