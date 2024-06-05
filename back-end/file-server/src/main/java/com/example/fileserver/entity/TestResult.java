package com.example.fileserver.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TestResult {
    // Getters and setters
    private int testCaseID;
    private String expectedOutput;
    private String actualOutput;
    private String correctness;
    private String time;

    public TestResult(int testCaseID, String expectedOutput, String actualOutput, String correctness, String time) {
        this.testCaseID = testCaseID;
        this.expectedOutput = expectedOutput;
        this.actualOutput = actualOutput;
        this.correctness = correctness;
        this.time = time;
    }

}