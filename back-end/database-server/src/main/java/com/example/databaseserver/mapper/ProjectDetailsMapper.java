package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.ProjectDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;

@Mapper
public interface ProjectDetailsMapper extends BaseMapper<ProjectDetails> {

    @Select("SELECT * FROM project_details WHERE name = #{name}")
    ProjectDetails getDetailsByName(String name);

    @Insert("INSERT INTO project_details(name, sketch, thinking) VALUES(#{name}, #{sketch}, #{thinking})")
    void insertProjectDetails(@Param("name") String name, @Param("sketch") String sketch,  @Param("thinking") String thinking);
}