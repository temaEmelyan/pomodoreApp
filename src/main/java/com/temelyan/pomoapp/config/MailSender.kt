package com.temelyan.pomoapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@Configuration
class MailSender {
    val javaMailSender: JavaMailSender
        @Bean
        get() {
            val mailSender = JavaMailSenderImpl()
            mailSender.host = "smtp.gmail.com"
            mailSender.port = 587

            mailSender.username = "pomodoreApp@gmail.com"
            mailSender.password = System.getenv("POMODORE_APP_PASSWORD")

            val props = mailSender.javaMailProperties
            props["mail.transport.protocol"] = "smtp"
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.debug"] = "true"

            return mailSender
        }
}
