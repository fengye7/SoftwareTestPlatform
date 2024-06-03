package com.example.projecttestserver.controllers;

import com.example.projecttestserver.utils.TestExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestServicesController {

    @Autowired
    private TestExecutionService testExecutionService;

    @GetMapping("/run-tests")
    public String runTests(@RequestParam String serviceName) {
        try {
            testExecutionService.executeTests(serviceName);
            return serviceName + "测试执行成功";
        } catch (IOException | InterruptedException e) {
            return "Error running tests: " + e.getMessage();
        }
    }

    @GetMapping("/get-test-report")
    public String getTestReport(@RequestParam String serviceName) {
        try {
            return testExecutionService.getTestReport(serviceName);
        } catch (IOException e) {
            return "Error getting test report: " + e.getMessage();
        }
    }

    @GetMapping("/run-integration-test")
    public String runIntegrationTest() {
        try {
            testExecutionService.executeIntegrationTest();
            return "集成测试执行成功";
        } catch (IOException | InterruptedException e) {
            return "Error running tests: " + e.getMessage();
        }
    }

    @GetMapping("/get-integration-test-report")
    public String getIntegrationTestReport() {
        try {
            return testExecutionService.getIntegrationTestReport();
        } catch (IOException e) {
            return "Error getting test report: " + e.getMessage();
        }
    }

    @GetMapping("/run-system-test")
    public String runSystemTest() {
        try {
            testExecutionService.executeSystemTest();
            return "系统测试执行成功";
        } catch (IOException | InterruptedException e) {
            return "Error running tests: " + e.getMessage();
        }
    }

    @GetMapping("/get-system-test-report")
    public String getSystemTestReport() {
        try {
            return testExecutionService.getSystemTestReport();
        } catch (IOException e) {
            return "Error getting test report: " + e.getMessage();
        }
    }

    @PostMapping("/terminate-test")
    public String terminateTest() {
        try {
            String result = testExecutionService.terminateTest();
            return result + " " +"系统测试已终止";
        } catch (Exception e) {
            return "终止系统测试时出错: " + e.getMessage();
        }
    }
}