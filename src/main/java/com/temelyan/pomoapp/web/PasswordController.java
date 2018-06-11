package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.EmailService;
import com.temelyan.pomoapp.service.UserService;
import com.temelyan.pomoapp.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
public class PasswordController {
    private final UserService userService;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PasswordController(UserService userService, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/forgot")
    public String displayForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping(value = "/fail")
    public String fail(@RequestParam Map<String, String> params, Model model) {
        model.addAttribute("username", params.getOrDefault("username", ""));
        model.addAttribute("errorMessage", "Bad credentials");

        return "login";
    }

    @PostMapping(value = "/forgot")
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, Model model, HttpServletRequest request) {
        User user;
        try {
            user = userService.loadByEmail(userEmail);
        } catch (NotFoundException e) {
            model.addAttribute("successMessage", "A password reset link has been sent to " + userEmail);
            return "forgotPassword";
        }
        user.setResetToken(UUID.randomUUID().toString());
        userService.update(user);
        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
        passwordResetEmail.setFrom("support@demo.com");
        passwordResetEmail.setTo(user.getEmail());
        passwordResetEmail.setSubject("Password Reset Request");
        passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl
                + "/reset?token=" + user.getResetToken());
        emailService.sendEmail(passwordResetEmail);
        model.addAttribute("successMessage", "A password reset link has been sent to " + userEmail);
        return "forgotPassword";
    }

    @GetMapping(value = "/reset")
    public String displayResetPasswordPage(Model model, @RequestParam("token") String token) {
        User user = userService.findUserByResetToken(token);
        if (user != null) {
            model.addAttribute("resetToken", token);
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        }
        return "resetPassword";
    }

    @PostMapping(value = "/reset")
    public String setNewPassword(Model model, @RequestParam Map<String, String> requestParams) {
        User user = userService.findUserByResetToken(requestParams.get("resetToken"));
        if (user != null) {
            String newPassword = requestParams.get("newPassword");
            String confirmPassword = requestParams.get("confirmPassword");
            if (newPassword.equals(confirmPassword) && newPassword.length() >= 8) {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetToken(null);
                userService.update(user);
                return "redirect:login";
            } else {//todo add password validator
                if (!newPassword.equals(confirmPassword)) {
                    model.addAttribute("errorMessage", "Passwords do not match");
                } else if (newPassword.length() < 8) {
                    model.addAttribute("errorMessage", "Password should be at least 8 characters long");
                }
            }
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        }
        return "resetPassword";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:login");
    }
}
