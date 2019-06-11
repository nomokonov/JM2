package com.example.test;

import com.example.test.config.DataInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    @Bean(initMethod = "init")
    public DataInitializer initTestData() {
        return new DataInitializer();
    }
}
