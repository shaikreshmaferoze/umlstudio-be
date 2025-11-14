package com.UMLStudio.backend.service;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.dto.ProjectDto;
import com.UMLStudio.backend.dto.ProjectRequest;
import com.UMLStudio.backend.dto.ProjectResponse;
import com.UMLStudio.backend.exception.ResourceNotFoundException;
import com.UMLStudio.backend.model.Project;
import com.UMLStudio.backend.model.ProjectAccess;
import com.UMLStudio.backend.model.User;
import com.UMLStudio.backend.repository.ProjectAccessRepository;
import com.UMLStudio.backend.repository.interfaces.ProjectAccessRepositoryPort;
import com.UMLStudio.backend.repository.interfaces.ProjectRepositoryPort;
import com.UMLStudio.backend.service.interfaces.ProjectAccessManagerPort;
import com.UMLStudio.backend.service.interfaces.ProjectServicePort;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService implements ProjectServicePort {

    private final ProjectRepositoryPort projectRepository;
    private final ProjectAccessManagerPort projectAccessManager;
    private final ModelMapper modelMapper;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project(request.getProjectName(), request.getProjectDescription(),request.getProjectDetails());
        Project saved = projectRepository.save(project);
        User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId=user.getUserId();
        projectAccessManager.saveAccess(new ProjectAccess(userId,saved.getProjectId(),saved.getCreatedAt(),AccessPolicy.Developer));
        return modelMapper.map(saved, ProjectResponse.class);
    }

    @Override
    public List<ProjectDto> listProjects(List<Long> projectIds) {
        return projectRepository.findAllById(projectIds)
            .stream()
            .map(project -> modelMapper.map(project, ProjectDto.class))
            .toList();
    }

    @Override
    public Optional<ProjectResponse> getProject(Long id) {
        Optional<ProjectResponse> project = projectRepository.findById(id).map((entity)->modelMapper.map(entity, ProjectResponse.class));
        return project;
    }

    @Override
    public ProjectResponse updateProject(ProjectRequest project) {
        return modelMapper.map(projectRepository.save(modelMapper.map(project,Project.class)), ProjectResponse.class);
    }
}
