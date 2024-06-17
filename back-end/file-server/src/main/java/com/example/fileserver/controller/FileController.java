package com.example.fileserver.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.mozilla.universalchardet.UniversalDetector;

@RestController
@RequestMapping("/files")
public class FileController {

    private static final String BASE_DIR = "file-server\\src\\main\\resources\\assets\\files";

    private boolean visitDirFolder(@RequestParam MultipartFile file, String directoryPath) throws IOException {
        String filePath = directoryPath + "/" + file.getOriginalFilename();
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean dirCreated = directory.mkdirs();
            if (!dirCreated) {
                System.out.println("创建目录失败: " + directoryPath);
                return true;
            }
        }
        Files.write(Paths.get(filePath), file.getBytes());
        return false;
    }

    @GetMapping("/scripts")
    public List<String> getScripts(@RequestParam String projectName) {
        System.out.println("获取项目的脚本: " + projectName);
        File dir = new File(BASE_DIR + "/scripts/" + projectName);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("目录不存在或不是目录。");
            return Collections.emptyList();
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".js"));
        if (files == null || files.length == 0) {
            System.out.println("未找到脚本文件。");
            return Collections.emptyList();
        }
        List<String> scriptNames = Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
        System.out.println("找到的脚本文件: " + scriptNames);
        return scriptNames;
    }

    @GetMapping("/tests")
    public List<String> getTests(@RequestParam String projectName) {
        System.out.println("获取项目的测试文件: " + projectName);
        File dir = new File(BASE_DIR + "/tests/" + projectName);
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("目录不存在或不是目录。");
            return Collections.emptyList();
        }
        File[] files = dir.listFiles((d, name) -> name.endsWith(".csv"));
        if (files == null || files.length == 0) {
            System.out.println("未找到测试文件。");
            return Collections.emptyList();
        }
        List<String> testNames = Arrays.stream(files)
                .map(File::getName)
                .collect(Collectors.toList());
        System.out.println("找到的测试文件: " + testNames);
        return testNames;
    }

    @PostMapping("/uploadScript")
    public String uploadScript(@RequestParam String projectName, @RequestParam MultipartFile file) throws IOException {
        System.out.println("上传项目脚本: " + projectName);
        String directoryPath = BASE_DIR + "/scripts/" + projectName;
        if (visitDirFolder(file, directoryPath)) return "创建目录失败。";
        System.out.println("脚本上传成功: " + file.getOriginalFilename());
        return "脚本上传成功: " + file.getOriginalFilename();
    }

    @PostMapping("/uploadTestSet")
    public String uploadTestSet(@RequestParam String projectName, @RequestParam MultipartFile file) throws IOException {
        System.out.println("上传项目测试集: " + projectName);
        String directoryPath = BASE_DIR + "/tests/" + projectName;

        if (visitDirFolder(file, directoryPath)) {
            return "创建目录失败。";
        }

        // 保存文件到服务器
        Path filePath = Paths.get(directoryPath, file.getOriginalFilename());

        // 如果文件已经存在，删除文件（或选择不同的策略，例如覆盖文件，或者抛出异常）
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        Files.copy(file.getInputStream(), filePath);

        // 检测并转换文件编码
        Charset charset = detectCharset(filePath);
        if (!charset.equals(StandardCharsets.UTF_8)) {
            // 如果不是UTF-8编码，转换为UTF-8并保存
            List<String> lines = Files.readAllLines(filePath, charset);
            Files.delete(filePath);
            Files.write(filePath, lines, StandardCharsets.UTF_8);
        }

        System.out.println("测试集上传成功: " + file.getOriginalFilename());
        return "测试集上传成功: " + file.getOriginalFilename();
    }

    private Charset detectCharset(Path filePath) throws IOException {
        byte[] buf = new byte[4096];
        try (java.io.InputStream fis = Files.newInputStream(filePath)) {
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
                detector.handleData(buf, 0, nread);
            }
            detector.dataEnd();
            String encoding = detector.getDetectedCharset();
            if (encoding != null) {
                return Charset.forName(encoding);
            } else {
                return StandardCharsets.UTF_8; // Default to UTF-8
            }
        }
    }


    @GetMapping("/scriptContent")
    public String getScriptContent(@RequestParam String projectName, @RequestParam String scriptName) {
        System.out.println("获取项目脚本内容: " + projectName + ", 脚本: " + scriptName);
        String filePath = BASE_DIR + "/scripts/" + projectName + "/" + scriptName;
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            System.out.println("文件不存在或是一个目录。");
            return "文件未找到。";
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            System.out.println("脚本内容获取成功。");
            return content;
        } catch (IOException e) {
            System.out.println("读取文件内容时出错: " + e.getMessage());
            return "读取文件内容时出错。";
        }
    }

    @GetMapping("/testSetContent")
    public ResponseEntity<String> getDatasetContent(@RequestParam String projectName, @RequestParam String testSetName) {
        System.out.println("获取项目测试集内容: " + projectName + ", 测试集: " + testSetName);
        String filePath = BASE_DIR + "/tests/" + projectName + "/" + testSetName;
        File file = new File(filePath);
        if (!file.exists() || file.isDirectory()) {
            System.out.println("文件不存在或是一个目录。");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("文件未找到。");
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
            System.out.println("测试集内容获取成功。");
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.TEXT_PLAIN)
                    .body(new String(content.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("读取文件内容时出错: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("读取文件内容时出错。");
        }
    }
}