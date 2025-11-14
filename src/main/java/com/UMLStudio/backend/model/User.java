package com.UMLStudio.backend.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    public User(String name, String username, String passwordHash) {
        this.name=name;
        this.username=username;
        this.passwordHash=passwordHash;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String name;

    @Column(unique = true)
    private String username;

    // @Column(unique = true)
    // private String email;

    private String passwordHash;
   

}

