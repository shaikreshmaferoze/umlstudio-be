package com.UMLStudio.backend.service.interfaces;

import java.util.List;
import com.UMLStudio.backend.model.ProjectAccess;

public interface ProjectAccessManagerPort {

    List<ProjectAccess> getAssignedProjects(Long userId);
    
    Boolean hasAccess(Long userId, Long projectId);

    ProjectAccess saveAccess(ProjectAccess projectAccess);

}
