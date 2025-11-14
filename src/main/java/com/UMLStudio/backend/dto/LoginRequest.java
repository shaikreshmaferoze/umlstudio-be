package com.UMLStudio.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    // One of username or email must be provided
    private String username;

    // private String email;

    @NotBlank
    private String password;

}

