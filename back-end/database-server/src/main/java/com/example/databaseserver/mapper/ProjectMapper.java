package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.Project;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {

    @Select("SELECT * FROM project")
    List<Project> getAllProjects();

    @Insert("INSERT INTO project(name, description, date, manager, resource) VALUES(#{name}, #{description}, #{date}, #{manager}, #{resource})")
    void insertProject(@Param("name") String name, @Param("description") String description, @Param("date") LocalDate date, @Param("manager") String manager, @Param("resource") String resource);

    @Delete("DELETE FROM project WHERE id = #{projectId}")
    void deleteProject(@Param("projectId") int projectId);
}
