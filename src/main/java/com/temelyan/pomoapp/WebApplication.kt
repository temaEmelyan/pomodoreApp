package com.temelyan.pomoapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}

