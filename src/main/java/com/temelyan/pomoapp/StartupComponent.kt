package com.temelyan.pomoapp

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.service.UserService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("develop")
class StartupComponent(private val userService: UserService) : CommandLineRunner {

    override fun run(vararg args: String) {
        userService.create(User(null, "test@gmail.com", "qwerqwer"))
        userService.create(User(null, "tema.emelyan@gmail.com", "password"))
    }
}
