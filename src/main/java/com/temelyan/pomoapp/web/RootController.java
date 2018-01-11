package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.Util.UserUtil;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.service.PomoService;
import com.temelyan.pomoapp.service.UserSevice;
import com.temelyan.pomoapp.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

import static com.temelyan.pomoapp.Util.ValidationUtil.assureIdConsistent;

@Controller
public class RootController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;

    @Autowired
    private UserSevice userSevice;

    @GetMapping("/")
    public String root() {
        logger.info("redirect from root to index.jsp");
        return "pomo";
    }

    @GetMapping("/log")
    public String openLog(Model model) {
        logger.info("redirecting to log page");

        List<Pomo> all = pomoService.getAll(AuthorizedUser.id());
        model.addAttribute("pomos", all);
        model.addAttribute("sumDuration", all.stream().mapToInt(Pomo::getDuration).sum());
        return "pomos";
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
        assureIdConsistent(userTo, AuthorizedUser.id());
        userSevice.update(userTo);
        AuthorizedUser.get().update(userTo);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        } else {
            userSevice.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getEmail();
        }
    }
}