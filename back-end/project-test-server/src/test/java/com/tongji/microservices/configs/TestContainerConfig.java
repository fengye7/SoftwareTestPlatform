//package com.tongji.microservices.configs;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@Configuration
//@Testcontainers
//public class TestContainerConfig {
//
//    @Container
//    public static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.26")
//            .withDatabaseName("testdb")
//            .withUsername("test")
//            .withPassword("test")
//            .withEnv("MYSQL_ROOT_HOST", "%")
//            .withEnv("MYSQL_ROOT_PASSWORD", "root")
//            .withEnv("MYSQL_MAX_CONNECTIONS", "200"); // 增加最大连接数
//
//    @DynamicPropertySource
//    static void mysqlProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mysqlContainer::getUsername);
//        registry.add("spring.datasource.password", mysqlContainer::getPassword);
//        // 显式设置 Hibernate 方言
//        registry.add("spring.jpa.properties.hibernate.dialect", () -> "org.hibernate.dialect.MySQL8Dialect");
//        // 配置连接池
//        registry.add("spring.datasource.hikari.maximum-pool-size", () -> "50"); // 设置连接池的最大连接数
//        registry.add("spring.datasource.hikari.minimum-idle", () -> "10"); // 设置连接池的最小空闲连接数
//        registry.add("spring.datasource.hikari.idle-timeout", () -> "30000"); // 设置连接池的空闲连接超时时间
//        registry.add("spring.datasource.hikari.max-lifetime", () -> "1800000"); // 设置连接池的最大连接存活时间
//    }
//
//    static {
//        mysqlContainer.start();  // 确保容器启动
//        // 设置环境变量
//        System.setProperty("spring.datasource.url", mysqlContainer.getJdbcUrl());
//        System.setProperty("spring.datasource.username", mysqlContainer.getUsername());
//        System.setProperty("spring.datasource.password", mysqlContainer.getPassword());
//        System.setProperty("spring.profiles.active", "test");
//    }
//}