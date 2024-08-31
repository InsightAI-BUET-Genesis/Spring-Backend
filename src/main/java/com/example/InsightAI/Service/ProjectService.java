package com.example.InsightAI.Service;

import com.example.InsightAI.Entity.Project;
import com.example.InsightAI.Interface.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/projects")
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    // Create a new project
    @PostMapping("/create")
    public ResponseEntity<Project> createProject(@RequestBody Map<String, String> projectData) {
        Project project = new Project();
        project.setId(UUID.randomUUID().toString());
        project.setName(projectData.get("name"));
        project.setDescription(projectData.get("description"));
        if(projectData.containsKey("imageUrl")) {
            project.setImageUrl(projectData.get("imageUrl"));
        }
        if(projectData.containsKey("theme")) {
            project.setTheme(projectData.get("theme"));
        }
        if(projectData.containsKey("tags")) {
            project.setTags(projectData.get("tags"));
        }
        project.setUserId(projectData.get("userId"));
        Project savedProject = projectRepo.save(project);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    // Get a project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable String id) {
        Optional<Project> project = projectRepo.findById(id);
        return project.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get all projects for a specific user by userId
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Project>> getProjectsByUserId(@PathVariable String userId) {
        List<Project> projects = projectRepo.findByUserId(userId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    // Edit a project (update image, title, description, theme, and tags)
    @PutMapping("/{id}")
    public ResponseEntity<Project> editProject(@PathVariable String id, @RequestBody Map<String, String> projectData) {
        Optional<Project> projectOpt = projectRepo.findById(id);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            if(projectData.containsKey("name")){
                project.setName(projectData.get("name"));
            }
            if(projectData.containsKey("description")){
                project.setDescription(projectData.get("description"));
            }
            if(projectData.containsKey("imageUrl")){
                project.setImageUrl(projectData.get("imageUrl"));
            }
            if(projectData.containsKey("theme")){
                project.setTheme(projectData.get("theme"));
            }
            if(projectData.containsKey("tags")){
                project.setTags(projectData.get("tags"));
            }

            Project savedProject = projectRepo.save(project);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update only the tasks of a project
    @PutMapping("/{id}/tasks")
    public ResponseEntity<Project> updateProjectTasks(@PathVariable String id, @RequestBody Map<String, String> taskUpdate) {
        Optional<Project> projectOpt = projectRepo.findById(id);
        if (projectOpt.isPresent()) {
            Project project = projectOpt.get();
            if(taskUpdate.containsKey("tasks")) {
                project.setTasks(taskUpdate.get("tasks"));
            }
            if(taskUpdate.containsKey("searchResults")) {
                project.setSearchResults(taskUpdate.get("searchResults"));
            }
            Project savedProject = projectRepo.save(project);
            return new ResponseEntity<>(savedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a project by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable String id) {
        Optional<Project> projectOpt = projectRepo.findById(id);
        if (projectOpt.isPresent()) {
            projectRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // 204 No Content
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
