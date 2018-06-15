package com.temelyan.pomoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class WebApplication {

    public static void main(String[] args) {
        new SpringApplication(WebApplication.class).run(args);
    }
}