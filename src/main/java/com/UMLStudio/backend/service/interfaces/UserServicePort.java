package com.UMLStudio.backend.service.interfaces;

import java.util.List;
import java.util.Optional;
import com.UMLStudio.backend.dto.ProjectDto;
import com.UMLStudio.backend.dto.ProjectRequest;
import com.UMLStudio.backend.model.Project;

public interface UserServicePort {

    public Optional<Project> viewProject(Long projectId);
    public Project saveProject(ProjectRequest project);
    public Boolean assignProject(Long projectId,Long userId,Long assigneeId);
    public List<ProjectDto> getProjectList(Long userId);
    
}
