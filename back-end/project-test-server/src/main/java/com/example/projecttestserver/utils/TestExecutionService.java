package com.example.projecttestserver.utils;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class TestExecutionService {
    private static final Logger logger = Logger.getLogger(TestExecutionService.class.getName());
    private final AtomicReference<Long> currentProcessPid = new AtomicReference<>();
    private final String BaseProjectPath = PathUtils.getSiblingDirectoryPath() + "/Microservices/";
    @Autowired
    private TestOutputWebSocketHandler webSocketHandler;

    public void executeTests(String serviceName) throws IOException, InterruptedException {
        // 设置项目路径
        String projectPath = BaseProjectPath + serviceName;

        // 清理之前的测试报告
        File reportDirectory = new File(projectPath + "/target/surefire-reports");
        if (reportDirectory.exists()) {
            FileSystemUtils.deleteRecursively(reportDirectory);
        }

        // 执行 Maven 测试命令
        ProcessBuilder processBuilder = new ProcessBuilder(getMavenCommand(), "test");
        webSocketTool(processBuilder, projectPath);
    }

    private String getMavenCommand() {
        // 获取 MAVEN_HOME 环境变量
        String mavenHome = System.getenv("MAVEN_HOME");
        if (mavenHome != null && !mavenHome.isEmpty()) {
            // 根据操作系统选择合适的 Maven 命令
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                return mavenHome + "\\bin\\mvn.cmd";
            } else {
                return mavenHome + "/bin/mvn";
            }
        } else {
            throw new IllegalStateException("MAVEN_HOME environment variable is not set.");
        }
    }

    public String getTestReport(String serviceName) throws IOException {
        // 获取测试报告路径
        String reportPath = BaseProjectPath + serviceName + "/target/surefire-reports";
        File reportDirectory = new File(reportPath);

        // 读取测试报告内容
        if (reportDirectory.exists() && reportDirectory.isDirectory()) {
            File[] reportFiles = reportDirectory.listFiles((dir, name) -> name.endsWith(".txt"));
            if (reportFiles != null && reportFiles.length > 0) {
                return new String(Files.readAllBytes(reportFiles[0].toPath()));
            }
        }
        return "测试报告未读到：" + reportPath;
    }

    public void executeIntegrationTest() throws IOException, InterruptedException {
        // 清理之前的测试报告
        File reportDirectory = new File("project-test-server/target/surefire-reports");
        if (reportDirectory.exists()) {
            FileSystemUtils.deleteRecursively(reportDirectory);
        }

        // 执行 Maven 测试命令
        ProcessBuilder processBuilder = new ProcessBuilder(getMavenCommand(), "test", "-Dtest=com.tongji.microservices.integrationtestserver.ServicesIntegrationTest");
        webSocketTool(processBuilder, "project-test-server");
    }

    private void webSocketTool(ProcessBuilder processBuilder, String projectPath) throws IOException, InterruptedException {
        processBuilder.directory(new File(projectPath));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        currentProcessPid.set(process.pid()); // 获取并存储进程的 PID

        InputStream inputStream = process.getInputStream();
        logger.info("程序启动，等待输出...");

        new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                logger.info("进入webSocket信息发布");
                while ((line = reader.readLine()) != null) {
                    webSocketHandler.sendMessage(line);
                    logger.info("过程输出: " + line);
                }
            } catch (IOException e) {
                logger.severe("读取过程输出错误：" + e.getMessage());
            }
        }).start();

        int exitCode = process.waitFor();
        webSocketHandler.sendMessage("使用退出代码完成的测试：" + exitCode);
        webSocketHandler.close();
        logger.info("完成测试流程，测试代码：" + exitCode);
    }

    public String getIntegrationTestReport() throws IOException {
        // 获取测试报告路径
        String reportPath = "project-test-server/target/surefire-reports";
        File reportDirectory = new File(reportPath);
        // 读取测试报告内容
        if (reportDirectory.exists() && reportDirectory.isDirectory()) {
            File[] reportFiles = reportDirectory.listFiles((dir, name) -> name.endsWith("ServicesIntegrationTest.txt"));
            if (reportFiles != null && reportFiles.length > 0) {
                return new String(Files.readAllBytes(reportFiles[0].toPath()));
            }
        }
        return "测试报告未读到：" + reportPath;
    }

    public void executeSystemTest() throws IOException, InterruptedException {
        // 清理之前的测试报告
        File reportDirectory = new File("project-test-server/target/surefire-reports");
        if (reportDirectory.exists()) {
            FileSystemUtils.deleteRecursively(reportDirectory);
        }

        // 执行 Maven 测试命令
        ProcessBuilder processBuilder = new ProcessBuilder(getMavenCommand(), "test", "-Dtest=com.tongji.microservices.systemtestserver.SystemTest");
        webSocketTool(processBuilder, "project-test-server");
    }


    public String getSystemTestReport() throws IOException {
        // 获取测试报告路径
        String reportPath = "project-test-server/target/surefire-reports";
        File reportDirectory = new File(reportPath);
        // 读取测试报告内容
        if (reportDirectory.exists() && reportDirectory.isDirectory()) {
            File[] reportFiles = reportDirectory.listFiles((dir, name) -> name.endsWith("SystemTest.txt"));
            if (reportFiles != null && reportFiles.length > 0) {
                return new String(Files.readAllBytes(reportFiles[0].toPath()));
            }
        }
        return "测试报告未读到：" + reportPath;
    }

    @SneakyThrows
    public String terminateTest() {
        Long pid = currentProcessPid.get();
        if (pid != null) {
            ProcessHandle processHandle = ProcessHandle.of(pid).orElse(null);
            if (processHandle != null && processHandle.isAlive()) {
                terminateProcessAndChildren(processHandle);
                logger.info("测试进程及其子进程已终止");
                webSocketHandler.sendMessage("测试进程及其子进程已终止");
                webSocketHandler.close();
                currentProcessPid.set(null); // 清理当前进程 PID
                return "已关闭正在运行的测试进程及其子进程";
            } else {
                logger.warning("没有正在运行的测试进程");
                return "没有正在运行的测试进程";
            }
        } else {
            logger.warning("没有正在运行的测试进程");
            return "没有正在运行的测试进程";
        }
    }

    private void terminateProcessAndChildren(ProcessHandle processHandle) {
        List<ProcessHandle> children = processHandle.children().collect(Collectors.toList());
        for (ProcessHandle child : children) {
            terminateProcessAndChildren(child);
        }
        processHandle.destroy();
        try {
            boolean terminated = processHandle.onExit().toCompletableFuture().get(5, java.util.concurrent.TimeUnit.SECONDS) != null; // 等待进程终止
            if (!terminated) {
                processHandle.destroyForcibly(); // 强制终止进程
            }
        } catch (Exception e) {
            logger.severe("无法终止进程：" + e.getMessage());
        }
    }
}