package com.UMLStudio.backend.repository.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.UMLStudio.backend.model.User;

public interface UserRepositoryPort{

    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
    User save(User user);
     
}