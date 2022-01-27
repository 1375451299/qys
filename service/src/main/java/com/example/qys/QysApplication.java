package com.example.qys;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.example.qys.dao")
public class QysApplication {

    public static void main(String[] args) {
        SpringApplication.run(QysApplication.class, args);
    }

}
