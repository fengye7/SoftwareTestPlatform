package com.example.databaseserver.service.impl;

import com.example.databaseserver.entity.TestResult;
import com.example.databaseserver.mapper.TestResultMapper;
import com.example.databaseserver.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestResultServiceImpl implements TestResultService {

    @Autowired
    private TestResultMapper testResultMapper;

    @Override
    public TestResult saveOrUpdateTestResult(TestResult testResult) {
        Optional<TestResult> existingResult = testResultMapper.findByTestCodeAndTestSet(testResult.getTestCode(), testResult.getTestSet());

        if (existingResult.isPresent()) {
            testResultMapper.updateTestResult(testResult);
        } else {
            testResultMapper.insertTestResult(testResult);
        }
        return testResult;
    }

    @Override
    public List<TestResult> getTestResultsByTestSet(String testSet, String projectName) {
        return testResultMapper.findByTestSet(testSet, projectName);
    }
}