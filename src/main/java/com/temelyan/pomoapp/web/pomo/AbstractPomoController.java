package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.service.PomoService;
import com.temelyan.pomoapp.service.ProjectService;
import com.temelyan.pomoapp.to.PomoTo;
import com.temelyan.pomoapp.to.ProjectTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public abstract class AbstractPomoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;

    @Autowired
    private ProjectService projectService;

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

    List<ProjectTo> getInDateRange(String from, String to) {
        logger.info("fetching entries for between {} and {}", from, to);

        LocalDate fromDate = LocalDate.parse(from, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate toDate = LocalDate.parse(to, DateTimeFormatter.ISO_LOCAL_DATE);

        return projectService.getAllWithPomosInDateRange(fromDate, toDate, AuthorizedUser.id());
    }
}