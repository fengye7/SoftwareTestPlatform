package com.example.projecttestserver.utils;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

@Service
public class DatabaseService {

    private static final String DB_HOST = "175.178.130.30";
    private static final String DB_NAME = "microservices";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "1234";
    private static final String BACKUP_FILE = "project-test-server/src/test/resources/backup.sql";

    public void backupDatabase() throws IOException, InterruptedException {
        System.out.println("数据库备份中……");
        String command = String.format("mysqldump -h %s -u %s -p%s %s > %s", DB_HOST, DB_USER, DB_PASSWORD, DB_NAME, BACKUP_FILE);
        executeCommand(command);
        System.out.println("备份完成");
    }

    public void restoreDatabase() throws IOException, InterruptedException {
        System.out.println("数据库还原中……");
        String command = String.format("mysql -h %s -u %s -p%s %s < %s", DB_HOST, DB_USER, DB_PASSWORD, DB_NAME, BACKUP_FILE);
        executeCommand(command);
        System.out.println("数据库已还原");
    }

    private void executeCommand(String command) throws IOException, InterruptedException {
        Process process = Runtime.getRuntime().exec(new String[]{"bash", "-c", command});
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }
            throw new RuntimeException("命令运行错误：" + exitCode);
        }
    }
}