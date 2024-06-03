package com.example.databaseserver.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("project_details")
public class ProjectDetails {
    @TableId
    int id;
    String name;
    String sketch;
    String thinking;
}
