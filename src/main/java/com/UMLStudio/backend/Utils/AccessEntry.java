package com.UMLStudio.backend.Utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessEntry {
    private AccessPolicy accessPolicy;
    private LocalDateTime assignedAt;
}
