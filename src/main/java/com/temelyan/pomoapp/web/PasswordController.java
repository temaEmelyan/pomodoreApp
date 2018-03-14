package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.EmailService;
import com.temelyan.pomoapp.service.UserService;
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

    @PostMapping(value = "/forgot")
    public String processForgotPasswordForm(@RequestParam("email") String userEmail, Model model, HttpServletRequest request) {
        User user = userService.loadByEmail(userEmail);
        if (user == null) {
            model.addAttribute("errorMessage", "We didn't find an account for that e-mail address.");
        } else {
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
        }
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
            if (requestParams.get("newPassword").equals(requestParams.get("confirmPassword"))) {
                user.setPassword(passwordEncoder.encode(requestParams.get("newPassword")));
                user.setResetToken(null);
                userService.update(user);
                return "redirect:login";
            } else {//todo add password validator
                model.addAttribute("errorMessage", "Passwords do not match");
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
