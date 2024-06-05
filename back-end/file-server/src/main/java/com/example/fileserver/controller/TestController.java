package com.example.fileserver.controller;

import com.example.fileserver.entity.TestRequest;
import com.example.fileserver.entity.TestResult;
import org.springframework.web.bind.annotation.*;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/start")
    public List<TestResult> startTest(@RequestBody TestRequest request) {
        String scriptPath = request.getCodeVersion();
        String testSetPath = request.getTestSet();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        if (engine == null) throw new RuntimeException("Failed to initialize javascript engine");

        try (Reader reader = Files.newBufferedReader(Paths.get(scriptPath))) {
            engine.eval(reader);
            Invocable invocable = (Invocable) engine;

            List<TestResult> results = Files.lines(Paths.get(testSetPath))
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] columns = line.split(",");
                        int testCaseID = Integer.parseInt(columns[0].trim());
                        int edge1 = Integer.parseInt(columns[1].trim());
                        int edge2 = Integer.parseInt(columns[2].trim());
                        int edge3 = Integer.parseInt(columns[3].trim());
                        String expectedOutput = columns[4].trim();
                        String time = LocalDate.now().toString();

                        try {
                            Object[] args = new Object[]{edge1, edge2, edge3};
                            String actualOutput = (String) invocable.invokeFunction("triangleJudge", args);
                            boolean isCorrect = expectedOutput.equals(actualOutput);
                            return new TestResult(testCaseID, expectedOutput, actualOutput, isCorrect ? "TRUE" : "FALSE", time);
                        } catch (NumberFormatException | ScriptException | NoSuchMethodException e) {
                            e.printStackTrace();
                            return new TestResult(testCaseID, expectedOutput, "Error processing line", "FALSE", time);
                        }
                    })
                    .collect(Collectors.toList());

            return results;
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
            throw new RuntimeException("运行js脚本错误：", e);
        }
    }
}
