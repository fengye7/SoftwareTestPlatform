package com.example.fileserver.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
public class TestRequest {
    // Getters and Setters
    @Getter
    private String projectName;
    private String codeVersion;
    private String testSet;

    private static final String BASE_DIR = "file-server/src/main/resources/assets/files";

    public String getCodeVersion() {
        return BASE_DIR + "/scripts/" + projectName + '/' + codeVersion;
    }

    public String getTestSet() {
        return BASE_DIR + "/tests/" + projectName + '/' + testSet;
    }

}
