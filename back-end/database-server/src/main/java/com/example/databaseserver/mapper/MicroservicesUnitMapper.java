package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.MicroservicesUnit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MicroservicesUnitMapper extends BaseMapper<MicroservicesUnit> {

    @Select("SELECT name FROM microservices_unit")
    List<String> getAllNames();

    @Select("SELECT description FROM microservices_unit WHERE name = #{name}")
    String getDescriptionByName(String name);
}