package com.UMLStudio.backend.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.UMLStudio.backend.Utils.AccessPolicy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "project_access",
    uniqueConstraints = @UniqueConstraint(columnNames = {"userId", "projectId"})
)
public class ProjectAccess {

    public ProjectAccess(Long userId, Long projectId, AccessPolicy accessPolicy) {
        this(userId, projectId, LocalDateTime.now(), accessPolicy);
    }

    public ProjectAccess(Long userId, Long projectId, LocalDateTime assignedAt, AccessPolicy accessPolicy) {
        this.userId = userId;
        this.projectId = projectId;
        this.assignedAt = assignedAt;
        this.accessPolicy = accessPolicy;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long projectId;

    @Enumerated(EnumType.STRING)
    private AccessPolicy accessPolicy;
    
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime assignedAt;
}
