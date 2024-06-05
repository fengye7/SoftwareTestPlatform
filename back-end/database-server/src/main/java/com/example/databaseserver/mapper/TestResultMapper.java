package com.example.databaseserver.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.databaseserver.entity.TestResult;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

public interface TestResultMapper extends BaseMapper<TestResult> {

    @Select("SELECT * FROM test_results WHERE testCode = #{testCode} AND testSet = #{testSet}")
    Optional<TestResult> findByTestCodeAndTestSet(@Param("testCode") String testCode, @Param("testSet") String testSet);

    @Select("SELECT * FROM test_results WHERE testSet = #{testSet}")
    List<TestResult> findByTestSet(@Param("testSet") String testSet);

    @Insert("INSERT INTO test_results(testCode, testSet, correctCount, totalCount, defectDescription, describer) " +
            "VALUES(#{testCode}, #{testSet}, #{correctCount}, #{totalCount}, #{defectDescription}, #{describer})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTestResult(TestResult testResult);

    @Update("UPDATE test_results SET correctCount = #{correctCount}, totalCount = #{totalCount}, " +
            "defectDescription = #{defectDescription}, describer = #{describer} " +
            "WHERE testCode = #{testCode} AND testSet = #{testSet}")
    void updateTestResult(TestResult testResult);
}