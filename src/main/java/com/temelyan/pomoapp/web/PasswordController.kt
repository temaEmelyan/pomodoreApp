package com.temelyan.pomoapp.web

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.service.EmailService
import com.temelyan.pomoapp.service.UserService
import com.temelyan.pomoapp.util.exception.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import java.util.*
import javax.servlet.http.HttpServletRequest

@Controller
class PasswordController @Autowired
constructor(private val userService: UserService, private val emailService: EmailService, private val passwordEncoder: PasswordEncoder) {

    @GetMapping("/forgot")
    fun displayForgotPasswordPage(): String {
        return "forgotPassword"
    }

    @PostMapping("/forgot")
    fun processForgotPasswordForm(@RequestParam("email") userEmail: String, model: Model, request: HttpServletRequest): String {
        val user: User
        try {
            user = userService.loadByEmail(userEmail)
        } catch (e: NotFoundException) {
            model.addAttribute("successMessage", "A password reset link has been sent to $userEmail")
            return "forgotPassword"
        }

        user.resetToken = UUID.randomUUID().toString()
        userService.update(user)
        val appUrl = request.scheme + "://" + request.serverName + ":" + request.serverPort

        val passwordResetEmail = SimpleMailMessage()
        passwordResetEmail.from = "support@demo.com"
        passwordResetEmail.setTo(user.email!!)
        passwordResetEmail.subject = "Password Reset Request"
        passwordResetEmail.text = ("To reset your password, click the link below:\n" + appUrl
                + "/reset?token=" + user.resetToken)
        emailService.sendEmail(passwordResetEmail)
        model.addAttribute("successMessage", "A password reset link has been sent to $userEmail")
        return "forgotPassword"
    }

    @GetMapping("/reset")
    fun displayResetPasswordPage(model: Model, @RequestParam("token") token: String): String {
        val user = userService.findUserByResetToken(token)
        if (user != null) {
            model.addAttribute("resetToken", token)
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.")
        }
        return "resetPassword"
    }

    @PostMapping("/reset")
    fun setNewPassword(model: Model, @RequestParam requestParams: Map<String, String>): String {
        val token = requestParams["resetToken"]
        var user: User? = null
        if (token != null) {
            user = userService.findUserByResetToken(token)
        }
        if (user != null) {
            val newPassword = requestParams["newPassword"] ?: error("new password should not be null")
            val confirmPassword = requestParams["confirmPassword"]
            if (newPassword == confirmPassword && newPassword.length >= 8) {
                user.password = passwordEncoder.encode(newPassword)
                user.resetToken = null
                userService.update(user)
                return "redirect:login"
            } else {//todo add password validator
                if (newPassword != confirmPassword) {
                    model.addAttribute("errorMessage", "Passwords do not match")
                } else if (newPassword.length < 8) {
                    model.addAttribute("errorMessage", "Password should be at least 8 characters long")
                }
            }
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.")
        }
        return "resetPassword"
    }

}
