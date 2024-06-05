package com.example.databaseserver.entity;

import lombok.Getter;
import lombok.Setter;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@Getter
@Setter
@TableName("test_results")
public class TestResult {

    @TableId
    private Long id;

    private String testCode;

    private String testSet;

    private Integer correctCount;

    private Integer totalCount;

    private String defectDescription;

    private String describer;
}