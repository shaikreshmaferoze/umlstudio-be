package com.UMLStudio.backend.service;

import org.springframework.stereotype.Service;

import com.UMLStudio.backend.service.interfaces.ProjectAccessPolicyPort;


@Service
public class DeveloperPolicy implements ProjectAccessPolicyPort {

    @Override
    public Boolean canSave() {
        return true;
    }

}
