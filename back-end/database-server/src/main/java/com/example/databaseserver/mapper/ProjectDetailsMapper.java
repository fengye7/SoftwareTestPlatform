package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.ProjectDetails;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProjectDetailsMapper extends BaseMapper<ProjectDetails> {

    @Select("SELECT * FROM project_details WHERE name = #{name}")
    ProjectDetails getDetailsByName(String name);
}