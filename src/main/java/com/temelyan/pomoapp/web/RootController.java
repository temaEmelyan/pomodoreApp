package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.UserUtil;
import com.temelyan.pomoapp.validator.UserValidator;
import com.temelyan.pomoapp.web.user.AbstractUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;

@SuppressWarnings("SameReturnValue")
@Controller
public class RootController extends AbstractUserController {

    private final UserValidator userValidator;

    @Autowired
    public RootController(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @GetMapping("/")
    public String root() {
        logger.info("redirect from root to pomo.html");
        return "pomo";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status) {
        super.update(userTo, AuthorizedUser.id());
        AuthorizedUser.get().update(userTo);
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "registration";
    }

    @PostMapping("/registration")
    public String saveRegister(UserTo userTo, BindingResult bindingResult, SessionStatus status) {
        userValidator.validate(userTo, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login";
        }
    }
}