package com.temelyan.pomoapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebApplication {

    public static void main(String[] args) {
        System.out.println("Env home folder is: " + System.getenv("HOME"));
        new SpringApplication(WebApplication.class).run(args);
    }
}