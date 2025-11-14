package com.UMLStudio.backend.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectResponse {
    private Long projectId;
    private String projectName;
    private String projectDescription;
    private LocalDateTime projectCreatedAt;
}

