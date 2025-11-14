package com.UMLStudio.backend.repository;

import com.UMLStudio.backend.model.Project;
import com.UMLStudio.backend.repository.interfaces.ProjectRepositoryPort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>,ProjectRepositoryPort{
    
}

