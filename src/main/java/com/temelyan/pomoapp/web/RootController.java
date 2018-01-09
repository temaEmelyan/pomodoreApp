package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.AuthorisedUser;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.service.PomoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RootController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;

    @GetMapping("/")
    public String root() {
        logger.info("redirect from root to index.jsp");
        return "pomo";
    }

    @GetMapping("/log")
    public String openLog(Model model) {
        logger.info("redirecting to log page");

        List<Pomo> all = pomoService.getAll(AuthorisedUser.getId());
        model.addAttribute("pomos", all);
        model.addAttribute("sumDuration", all.stream().mapToInt(Pomo::getDuration).sum());
        return "pomos";
    }
}