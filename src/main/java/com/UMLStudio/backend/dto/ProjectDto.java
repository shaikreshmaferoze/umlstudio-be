package com.UMLStudio.backend.dto;

import java.time.LocalDateTime;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.fasterxml.jackson.databind.JsonNode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {

    private long userId;
    private long projectId;
    private AccessPolicy accessPolicy;
    private String projectName;
    private String projectDescription;
    private LocalDateTime createdAt;
    private JsonNode projectDetails;
    private LocalDateTime assignedAt;

}
