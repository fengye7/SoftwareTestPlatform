package com.example.databaseserver.service;

import com.example.databaseserver.entity.TestResult;
import java.util.List;

public interface TestResultService {
    TestResult saveOrUpdateTestResult(TestResult testResult);
    List<TestResult> getTestResultsByTestSet(String testSet);
}