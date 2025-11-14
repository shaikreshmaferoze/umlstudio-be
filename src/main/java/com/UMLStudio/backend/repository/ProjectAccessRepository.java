package com.UMLStudio.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.model.ProjectAccess;
import com.UMLStudio.backend.repository.interfaces.ProjectAccessRepositoryPort;

@Repository
public interface ProjectAccessRepository extends JpaRepository<ProjectAccess, Long>,ProjectAccessRepositoryPort {

    List<ProjectAccess> findAllByUserId(Long userId);
    @Query("SELECT p.accessPolicy FROM ProjectAccess p WHERE p.userId = :userId AND p.projectId = :projectId")
    AccessPolicy findAccessPolicyByUserIdAndProjectId(@Param("userId") Long userId,@Param("projectId") Long projectId);

}
