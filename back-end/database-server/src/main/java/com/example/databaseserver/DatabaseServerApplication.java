package com.example.databaseserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.databaseserver.mapper")
public class DatabaseServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseServerApplication.class, args);
    }
}
