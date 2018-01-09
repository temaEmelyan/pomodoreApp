package com.temelyan.pomoapp.web;

import com.temelyan.pomoapp.AuthorisedUser;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.service.PomoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/ajax")
public class PomoController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;

    @PostMapping("/add/{length}")
    public void add(@PathVariable("length") int length) {
        logger.info("adding pomodoro");
        pomoService.add(new Pomo(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), length), AuthorisedUser.getId());
    }
}
