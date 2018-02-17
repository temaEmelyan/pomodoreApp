package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.service.PomoService;
import com.temelyan.pomoapp.to.PomoTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public abstract class AbstractPomoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;

    List<PomoTo> getAll() {
        int userId = AuthorizedUser.id();
        logger.info("getAll for User {}", userId);
        return pomoService.getAllForUser(userId);
    }

    void add(int length, int projecId) {
        logger.info("add Pomo with the length {}", length);
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        pomoService.add(new Pomo(now, length), projecId);
    }
}