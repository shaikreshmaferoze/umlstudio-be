package com.UMLStudio.backend.service.interfaces;

import com.UMLStudio.backend.dto.ProjectDto;
import com.UMLStudio.backend.dto.ProjectRequest;
import com.UMLStudio.backend.dto.ProjectResponse;
import com.UMLStudio.backend.model.Project;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.Optional;

public interface ProjectServicePort {
    ProjectResponse createProject(ProjectRequest request);
    Optional<ProjectResponse> getProject(Long id);
    ProjectResponse updateProject(ProjectRequest project);
    List<ProjectDto> listProjects(List<Long> projectIds);
}

