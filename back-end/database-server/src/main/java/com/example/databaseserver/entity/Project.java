package com.example.databaseserver.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;

@Data
@TableName("project")
public class Project {

    @TableId
    private int id;
    private String name;
    private String description;
    private LocalDate date;
    private String manager;
    private String resource;
}
