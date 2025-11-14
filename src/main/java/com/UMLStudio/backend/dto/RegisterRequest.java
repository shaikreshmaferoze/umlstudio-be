package com.UMLStudio.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String username;

    // @NotBlank
    // @Email
    // @Size(max = 255)
    // private String email;

    @NotBlank
    @Size(min = 6, max = 255)
    private String password;

}

