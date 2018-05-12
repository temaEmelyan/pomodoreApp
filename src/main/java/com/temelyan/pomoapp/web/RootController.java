package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.service.ProjectService;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.UserUtil;
import com.temelyan.pomoapp.validator.UserUpdateValidator;
import com.temelyan.pomoapp.validator.UserValidator;
import com.temelyan.pomoapp.web.user.AbstractUserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.Set;

@SuppressWarnings("SameReturnValue")
@Controller
public class RootController extends AbstractUserController {

    private final UserValidator userValidator;
    private final ProjectService projectService;
    private final UserUpdateValidator userUpdateValidator;

    @Autowired
    public RootController(UserValidator userValidator,
                          UserUpdateValidator userUpdateValidator,
                          ProjectService projectService) {
        this.userValidator = userValidator;
        this.userUpdateValidator = userUpdateValidator;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String root(Model model) {
        logger.info("redirect from root to pomo.html for user {}", AuthorizedUser.get());
        UserTo userTo = AuthorizedUser.get().getUserTo();
        Set<Project> allForUser = projectService.getAllForUser(userTo.getId());
        userTo.setProjects(allForUser);
        model.addAttribute("user", userTo);
        model.addAttribute("project", new ArrayList<>(allForUser).get(0));
        return "pomo";
    }

    @GetMapping(value = "/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }
        return "login";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserTo userTo = new UserTo();
        userTo.setEmail(AuthorizedUser.get().getUserTo().getEmail());
        model.addAttribute("userTo", userTo);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(UserTo userTo, BindingResult bindingResult, SessionStatus status) {
        userUpdateValidator.validate(userTo, bindingResult);
        if (bindingResult.hasErrors()) {
            return "profile";
        } else {
            userTo.setPassword(userTo.getNewPassword());
            super.update(userTo, AuthorizedUser.id());
            AuthorizedUser.get().update(userTo);
            return "redirect:/";
        }
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

    @GetMapping("/log")
    public String openLog() {
        logger.info("opening log page");
        return "pomos";
    }
}