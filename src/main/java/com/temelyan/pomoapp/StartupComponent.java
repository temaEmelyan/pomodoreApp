package com.temelyan.pomoapp;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("mockdata")
public class StartupComponent implements CommandLineRunner {
    private final UserService userService;

    @Autowired
    public StartupComponent(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.create(new User(null, "test@gmail.com", "qwerqwer"));
        userService.create(new User(null, "tema.emelyan@gmail.com", "qwerqwer"));
    }
}
