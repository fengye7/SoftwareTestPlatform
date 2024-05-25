package com.example.fileserver.controller;

import com.example.fileserver.entity.TestRequest;
import org.springframework.web.bind.annotation.*;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("/start")
    public List<String> startTest(@RequestBody TestRequest request) {
        String scriptPath = request.getCodeVersion();
        String testSetPath = request.getTestSet();

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        if (engine == null) throw new RuntimeException("Failed to initialize javascript engine");

        try (Reader reader = Files.newBufferedReader(Paths.get(scriptPath))) {
            engine.eval(reader);
            Invocable invocable = (Invocable) engine;

            List<String> results = Files.lines(Paths.get(testSetPath))
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] columns = line.split(",");
                        try {
                            Object[] args = new Object[]{
                                    Integer.parseInt(columns[1].trim()),
                                    Integer.parseInt(columns[2].trim()),
                                    Integer.parseInt(columns[3].trim())
                            };
                            return (String) invocable.invokeFunction("triangleJudge", args);
                        } catch (NumberFormatException | ScriptException | NoSuchMethodException e) {
                            e.printStackTrace();
                            return "Error processing line: " + line;
                        }
                    })
                    .collect(Collectors.toList());

            return results;
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
            throw new RuntimeException("Error running test script", e);
        }
    }
}
