package com.example.fileserver.controller;

import com.example.fileserver.entity.TestRequest;
import com.example.fileserver.entity.TestResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/test")
public class TestController {
    @PostMapping("/start")
    public List<TestResult> startTest(@RequestBody TestRequest request) {
        String scriptPath = request.getCodeVersion();
        String testSetPath = request.getTestSet();

        // 判断传入的路径是否正确
        if (!Files.exists(Paths.get(scriptPath))) {
            throw new RuntimeException("Script path 不存在: " + scriptPath);
        }
        if (!Files.exists(Paths.get(testSetPath))) {
            throw new RuntimeException("Test set path 不存在: " + testSetPath);
        }

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        if (engine == null) throw new RuntimeException("Failed to initialize javascript engine");

        try (Reader reader = Files.newBufferedReader(Paths.get(scriptPath), StandardCharsets.UTF_8)) {
            engine.eval(reader);
            Invocable invocable = (Invocable) engine;

            List<String> lines = Files.readAllLines(Paths.get(testSetPath), Charset.forName("GBK"));
            if (lines.isEmpty()) {
                throw new RuntimeException("Test set is empty");
            }

            // 解析表头，找到 expectedOutput 的位置
            String headerLine = lines.get(0);
            String[] headers = headerLine.split(",");
            int expectedOutputIndex = IntStream.range(0, headers.length).filter(i -> headers[i].trim().equalsIgnoreCase("ExpectedOutput")).findFirst().orElse(-1);

            if (expectedOutputIndex == -1) {
                throw new RuntimeException("ExpectedOutput 列未找到");
            }

            List<TestResult> results = lines.stream()
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] columns = line.split(",");
                        int testCaseID = Integer.parseInt(columns[0].trim());
                        String expectedOutput = columns[expectedOutputIndex].trim();
                        String time = LocalDate.now().toString();

                        // 提取参数
                        Object[] args = new Object[expectedOutputIndex - 1];
                        for (int i = 1, j = 0; i < expectedOutputIndex; i++) {
                            args[j++] = columns[i].trim();
                        }
                        System.out.println("测试集："+args[0]+' '+args[1]+' '+args[2]);

                        try {
                            String actualOutput = (String) invocable.invokeFunction("executeTest", new Object[]{args});
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