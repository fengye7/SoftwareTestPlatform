package com.example.databaseserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.databaseserver.entity.Project;
import com.example.databaseserver.entity.ProjectDetails;
import com.example.databaseserver.mapper.ProjectDetailsMapper;
import com.example.databaseserver.mapper.ProjectMapper;
import com.example.databaseserver.service.ProjectService;
import com.example.databaseserver.service.dto.ProjectDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private ProjectDetailsMapper projectDetailsMapper;

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

    @Override
    public ProjectDetailsDTO getDetailsByName(String name) {
        ProjectDetails projectDetails = projectDetailsMapper.getDetailsByName(name);
        Project project = projectMapper.selectOne(new QueryWrapper<Project>().eq("name", name));

        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        if (projectDetails != null) {
            projectDetailsDTO.setName(projectDetails.getName());
            projectDetailsDTO.setSketch(projectDetails.getSketch());
            projectDetailsDTO.setThinking(projectDetails.getThinking());
        }

        if (project != null) {
            projectDetailsDTO.setDescription(project.getDescription());
            projectDetailsDTO.setDate(project.getDate());
            projectDetailsDTO.setManager(project.getManager());
            projectDetailsDTO.setResource(project.getResource());
        }

        return projectDetailsDTO;
    }
}
