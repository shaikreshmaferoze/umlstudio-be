package com.UMLStudio.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.model.ProjectAccess;
import com.UMLStudio.backend.repository.interfaces.ProjectAccessRepositoryPort;
import com.UMLStudio.backend.service.interfaces.ProjectAccessManagerPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectAccessManager implements ProjectAccessManagerPort{


    private final ProjectAccessRepositoryPort projectAccessRepository;
    private final ProjectAccessPolicyContext projectAccessPolicy;


    @Override
    public List<ProjectAccess> getAssignedProjects(Long userId) {
        return projectAccessRepository.findAllByUserId(userId);
    }

    @Override
    public Boolean hasAccess(Long userId, Long projectId) {
        AccessPolicy policy=projectAccessRepository.findAccessPolicyByUserIdAndProjectId(userId,projectId);
        return projectAccessPolicy.getPolicy(policy).canSave();
    }

    @Override
    public ProjectAccess saveAccess(ProjectAccess projectAccess) {
        return projectAccessRepository.save(projectAccess);
    }

}
