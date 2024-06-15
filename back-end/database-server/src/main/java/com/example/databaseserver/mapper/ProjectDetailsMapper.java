package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.ProjectDetails;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface ProjectDetailsMapper extends BaseMapper<ProjectDetails> {

    @Select("SELECT * FROM project_details WHERE name = #{name}")
    ProjectDetails getDetailsByName(String name);

    @Insert("INSERT INTO project_details(name, sketch, thinking) VALUES(#{name}, #{sketch}, #{thinking})")
    void insertProjectDetails(@Param("name") String name, @Param("sketch") String sketch,  @Param("thinking") String thinking);

    @Update("Update project_details SET name = #{newName} WHERE name = #{oldName}")
    void changeName(@Param("oldName") String oldName, @Param("newName") String newName);

    @Delete("DELETE FROM project_details WHERE name = #{name}")
    void deleteProjectDetails(@Param("name") String name);
}