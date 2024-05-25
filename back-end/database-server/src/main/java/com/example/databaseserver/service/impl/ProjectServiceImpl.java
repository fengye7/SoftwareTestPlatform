package com.example.databaseserver.service.impl;

import com.example.databaseserver.entity.Project;
import com.example.databaseserver.mapper.ProjectMapper;
import com.example.databaseserver.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public List<Project> getAllProjects() {
        return projectMapper.getAllProjects();
    }

    @Override
    public void insertProject(String name, String description, LocalDate date, String manager, String resource) {
        projectMapper.insertProject(name, description, date, manager, resource);
    }

    @Override
    public void deleteProject(int projectId) {
        projectMapper.deleteProject(projectId);
    }
}
