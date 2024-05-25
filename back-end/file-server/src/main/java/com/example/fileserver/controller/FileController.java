package com.example.fileserver.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String BASE_DIR = "file-server\\src\\main\\resources\\assets\\files";

    @GetMapping("/scripts")
    public List<String> getScripts(@RequestParam String projectName) {
        System.out.println("Getting scripts for project: " + projectName);
        File dir = new File(BASE_DIR + "/scripts/" + projectName);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory.");
            return Collections.emptyList();
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".js"));
        if (files == null || files.length == 0) {
            System.out.println("No script files found.");
            return Collections.emptyList();
        }
        List<String> scriptNames = Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
        System.out.println("Script files found: " + scriptNames);
        return scriptNames;
    }

    @GetMapping("/tests")
    public List<String> getTests(@RequestParam String projectName) {
        System.out.println("Getting tests for project: " + projectName);
        File dir = new File(BASE_DIR + "/tests/" + projectName);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("Directory does not exist or is not a directory.");
            return Collections.emptyList();
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("No test files found.");
            return Collections.emptyList();
        }
        List<String> testNames = Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
        System.out.println("Test files found: " + testNames);
        return testNames;
    }

    @PostMapping("/uploadScript")
    public String uploadScript(@RequestParam String projectName, @RequestParam MultipartFile file) throws IOException {
        System.out.println("Uploading script for project: " + projectName);
        String directoryPath = BASE_DIR + "/scripts/" + projectName;
        String filePath = directoryPath + "/" + file.getOriginalFilename();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.out.println("Failed to create directory: " + directoryPath);
                return "Failed to create directory.";
            }
        }
        Files.write(Paths.get(filePath), file.getBytes());
        System.out.println("Script uploaded successfully: " + file.getOriginalFilename());
        return "Script uploaded successfully: " + file.getOriginalFilename();
    }

    @PostMapping("/uploadTestSet")
    public String uploadTestSet(@RequestParam String projectName, @RequestParam MultipartFile file) throws IOException {
        System.out.println("Uploading test set for project: " + projectName);
        String directoryPath = BASE_DIR + "/tests/" + projectName;
        String filePath = directoryPath + "/" + file.getOriginalFilename();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.out.println("Failed to create directory: " + directoryPath);
                return "Failed to create directory.";
            }
        }
        Files.write(Paths.get(filePath), file.getBytes());
        System.out.println("Test set uploaded successfully: " + file.getOriginalFilename());
        return "Test set uploaded successfully: " + file.getOriginalFilename();
    }
}
