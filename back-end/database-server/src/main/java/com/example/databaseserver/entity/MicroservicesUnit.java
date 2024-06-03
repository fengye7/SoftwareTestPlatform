package com.example.databaseserver.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("microservices_unit")
public class MicroservicesUnit {
    @TableId
    int id;
    String name;
    String description;
}