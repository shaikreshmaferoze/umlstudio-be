package com.UMLStudio.backend.service;

import org.springframework.stereotype.Service;

import com.UMLStudio.backend.service.interfaces.ProjectAccessPolicyPort;

@Service
public class ViewerPolicy implements ProjectAccessPolicyPort{

    @Override
    public Boolean canSave() {
        return false;
    }

}
