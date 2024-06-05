package com.example.databaseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@MapperScan("com.example.databaseserver.mapper")
@EnableJpaRepositories(basePackages = "com.example.databaseserver.mapper")
@EntityScan(basePackages = "com.example.databaseserver.entity") // 添加这一行
public class DatabaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseServerApplication.class, args);
    }
}
