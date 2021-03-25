package com.example.demo21032501;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication

@EnableJpaRepositories
public class Demo21032501Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo21032501Application.class, args);
    }

}
