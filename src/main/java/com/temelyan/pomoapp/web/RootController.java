package com.temelyan.pomoapp.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/")
    public String root() {
        logger.info("redirect from root to index.jsp");
        return "pomo";
    }

    @GetMapping("/log")
    public String openLog() {
        logger.info("redirecting to log page");
        return "pomos";
    }
}