package com.temelyan.pomoapp.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service("emailService")
class EmailServiceImpl @Autowired
constructor(private val mailSender: JavaMailSender) : EmailService {

    override fun sendEmail(email: SimpleMailMessage) {
        mailSender.send(email)
    }
}
