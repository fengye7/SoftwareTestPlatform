package com.example.projecttestserver.utils;

import java.io.File;

public class PathUtils {

    private static final String testTarget = "test-targets";

    // 获取与back-end同级目录的路径
    public static String getSiblingDirectoryPath() {
        // 获取当前工作目录
        String currentDir = System.getProperty("user.dir");
        // 获取back-end目录的父目录
        File parentDir = new File(currentDir).getParentFile();
        // 构建与back-end同级的目标目录路径
        String siblingDirPath = new File(parentDir, testTarget).getAbsolutePath();
        // 将路径中的所有反斜杠替换为正斜杠
        return siblingDirPath.replace("\\", "/");
    }

    public static void main(String[] args) {
        System.out.println(getSiblingDirectoryPath());
    }
}