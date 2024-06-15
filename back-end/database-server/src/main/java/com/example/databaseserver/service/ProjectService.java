package com.example.databaseserver.service;

import com.example.databaseserver.entity.Project;
import com.example.databaseserver.service.dto.ProjectDetailsDTO;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects();

    void insertProject(String name, String description, LocalDate date, String manager, String resource);

    void modifyProject(String oldName, String newName, String description, LocalDate date, String manager, String resource);

//    void deleteProject(int projectId);

    void deleteProject(String name);

    ProjectDetailsDTO getDetailsByName(String name);
}
