package com.UMLStudio.backend.controller;

import com.UMLStudio.backend.Utils.AccessPolicy;
import com.UMLStudio.backend.dto.ApiResponse;
import com.UMLStudio.backend.dto.ProjectDto;
import com.UMLStudio.backend.dto.ProjectRequest;
import com.UMLStudio.backend.dto.ProjectResponse;
import com.UMLStudio.backend.model.Project;
import com.UMLStudio.backend.model.User;
import com.UMLStudio.backend.service.interfaces.ProjectAccessManagerPort;
import com.UMLStudio.backend.service.interfaces.ProjectServicePort;
import com.UMLStudio.backend.service.interfaces.UserServicePort;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class ProjectController {

    private final ProjectServicePort projectService;
    private final ProjectAccessManagerPort projectAccessManager;
    private final UserServicePort userService;

    // @PostMapping
    // public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request) {
    //     ProjectResponse created = projectService.createProject(request);
    //     return ResponseEntity.status(HttpStatus.CREATED).body(created);
    // }

    // @GetMapping
    // public ResponseEntity<List<ProjectResponse>> listProjects() {
    //     List<ProjectResponse> list = projectService.listProjects();
    //     return ResponseEntity.ok(list);
    // }
 
    @GetMapping("/{projectId}")
    public ResponseEntity<?> getProject(@PathVariable Long projectId) {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user == null){
                return ResponseEntity.status(401).body(new ApiResponse("FAILED","invalid or missing token",null));
            }
            Long userId=user.getUserId();
            Optional<ProjectResponse> isProject=projectService.getProject(projectId);
            if(isProject.isEmpty()){
                return ResponseEntity.status(404).body(new ApiResponse("FAILED","Project not found for the given ID.",null));
            }
            if(projectAccessManager.hasAccess(userId, projectId)){
                ProjectResponse project=isProject.get();
                return ResponseEntity.ok().body(new ApiResponse("SUCCESS","Fetched Project Details Successfully!",project));
            }
            return ResponseEntity.status(403).body(new ApiResponse("FAILED","Authenticated user does not have permission to create or modify the project",null));
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse("FAILED","Something went wrong while fetching project list",null));
        }
    }

    @GetMapping("/getProjectList")
    public ResponseEntity<?> getProjectList(){
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user == null){
                return ResponseEntity.status(401).body(new ApiResponse("FAILED","invalid or missing token",null));
            }
            Long userId=user.getUserId();
            List<ProjectDto> projects=userService.getProjectList(userId);
            if(projects.isEmpty()){
                return ResponseEntity.status(404).body(new ApiResponse("FAILED","No projects found for the given user",null));
            }
            return ResponseEntity.ok().body(new ApiResponse("SUCCESS","Project list fetched successfully",projects));
        }
        catch(Exception e){
            return ResponseEntity.status(500).body(new ApiResponse("FAILED","Something went wrong while fetching project list",null));
        }
    } 

    @PostMapping("/saveProject")
    public ResponseEntity<?> saveProject(@RequestBody ProjectRequest project) {
        try{
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if(user == null){
                return ResponseEntity.status(401).body(new ApiResponse("FAILED","invalid or missing token",null));
            }
            else if(project.getProjectDetails()==null || project.getProjectName()==null){
                return ResponseEntity.badRequest().body(new ApiResponse("FAILED"," Invalid or incomplete project data provided.",null));
            }
            else if(project.getProjectId()==null){
                return ResponseEntity.ok().body(new ApiResponse("SUCCESS","Project Saved Successfully",projectService.createProject(project)));
            }
            Long userId=user.getUserId();
            if(projectAccessManager.hasAccess(userId, project.getProjectId())){
                if (projectService.getProject(project.getProjectId()).isEmpty()) {
                    return ResponseEntity.status(404).body(new ApiResponse("FAILED", "projectId provided but no matching project exists for update.", null));
                }
                ProjectResponse response=projectService.updateProject(project);    
                return ResponseEntity.ok().body(new ApiResponse("SUCCESS","Project Saved Successfully",response.getProjectId()));
            }
            return ResponseEntity.status(403).body(new ApiResponse("FAILED","Authenticated user does not have permission to create or modify the project",null));

        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(new ApiResponse("FAILED","Something went wrong while saving the project.",null));
        }
    }
    
    



}
