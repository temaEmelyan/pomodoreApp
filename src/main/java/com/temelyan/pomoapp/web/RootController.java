package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.service.PomoService;
import com.temelyan.pomoapp.service.UserSevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserSevice userSevice;

    @Autowired
    private PomoService pomoService;

    @GetMapping("/")
    public String root() {
        logger.info("redirect from root to index.jsp");
        return "index";
    }

    @GetMapping("/work")
    public String work() {
        logger.info("redirect from root to work.jsp");
        return "pomo";
    }
}