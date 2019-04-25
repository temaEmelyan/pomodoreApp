package com.temelyan.pomoapp.service

import org.springframework.mail.SimpleMailMessage
import org.springframework.scheduling.annotation.Async


interface EmailService {
    @Async
    fun sendEmail(email: SimpleMailMessage)
}
