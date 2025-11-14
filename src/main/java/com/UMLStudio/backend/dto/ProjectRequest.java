package com.UMLStudio.backend.dto;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRequest {

    //not adding id would make repeated copies of projects as .save is used for both updation and adding to the db
    private Long projectId;
    
    @NotBlank(message = "name is required")
    @Size(max = 255)
    private String projectName;

    @Size(max = 2000)
    private String projectDescription;

    private JsonNode projectDetails;

    // public ProjectRequest() {
    // }

    // public ProjectRequest(String name, String description) {
    //     this.name = name;
    //     this.description = description;
    // }

    // public String getName() {
    //     return name;
    // }

    // public void setName(String name) {
    //     this.name = name;
    // }

    // public String getDescription() {
    //     return description;
    // }

    // public void setDescription(String description) {
    //     this.description = description;
    // }
}

