package com.UMLStudio.backend.service;
import org.springframework.stereotype.Service;
import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.service.interfaces.ProjectAccessPolicyPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectAccessPolicyContext{

    private final DeveloperPolicy developerPolicy;
    private final ViewerPolicy viewerPolicy;

    public ProjectAccessPolicyPort getPolicy(AccessPolicy accessPolicy) {
        return switch (accessPolicy) {
            case AccessPolicy.Developer -> developerPolicy;
            case AccessPolicy.Viewer -> viewerPolicy;
        };
    }

}
