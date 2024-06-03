package com.example.databaseserver.controller;

import com.example.databaseserver.entity.Project;
import com.example.databaseserver.entity.ProjectDetails;
import com.example.databaseserver.service.ProjectService;
import com.example.databaseserver.service.dto.ProjectDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @PostMapping
    public void insertProject(@RequestParam String name, @RequestParam String description, @RequestParam LocalDate date,
                              @RequestParam String manager, @RequestParam String resource) {
        projectService.insertProject(name, description, date, manager, resource);
    }

    @DeleteMapping("/{id}")
    public void deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
    }

    @GetMapping("/project-details")
    public ProjectDetailsDTO getDetailsByName(@RequestParam String name) {
        return projectService.getDetailsByName(name);
    }
}
