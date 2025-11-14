package com.UMLStudio.backend.repository.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.model.ProjectAccess;

public interface ProjectAccessRepositoryPort extends JpaRepository<ProjectAccess, Long> {

    List<ProjectAccess> findAllByUserId(Long userId);

    AccessPolicy findAccessPolicyByUserIdAndProjectId(Long userId, Long projectId);

    // ProjectAccess save(ProjectAccess projectAccess);

}
