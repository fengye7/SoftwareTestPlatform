package com.example.databaseserver.controller;

import com.example.databaseserver.entity.TestResult;
import com.example.databaseserver.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test-results")
public class TestResultController {

    @Autowired
    private TestResultService testResultService;

    @PostMapping
    public TestResult saveOrUpdateTestResult(@RequestBody TestResult testResult) {
        return testResultService.saveOrUpdateTestResult(testResult);
    }

    @GetMapping("/get-by-testSet")
    public List<TestResult> getTestResultsByTestSet(@RequestParam String testSet, @RequestParam String projectName) {
        return testResultService.getTestResultsByTestSet(testSet, projectName);
    }
}