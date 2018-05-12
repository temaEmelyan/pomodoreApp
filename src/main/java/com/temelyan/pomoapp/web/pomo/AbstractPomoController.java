package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.PomoService;
import com.temelyan.pomoapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public abstract class AbstractPomoController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PomoService pomoService;
    @Autowired
    private UserService userService;

    void add(int length, int projectId, int clientTimeZone) {
        logger.info("add Pomo with the length {} for user {}", length, AuthorizedUser.get());

        LocalDateTime serverTimeNow = LocalDateTime.now();

        int serverTimeZone = ZoneOffset.systemDefault()
                .getRules()
                .getOffset(serverTimeNow)
                .get(ChronoField.OFFSET_SECONDS) / 3600;

        LocalDateTime clientsTime = serverTimeNow
                .truncatedTo(ChronoUnit.SECONDS)
                .plus(clientTimeZone - serverTimeZone, ChronoUnit.HOURS);

        pomoService.add(new Pomo(clientsTime, length), projectId);
    }

    protected User getUserWithPomosInDateRange(String from, String to) {
        logger.info("fetching pomos for dates between {} and {} for user {}", from, to, AuthorizedUser.get());
        return userService.getByIdWithPomosInDateRange(
                AuthorizedUser.id(),
                LocalDateTime.of(LocalDate.parse(from), LocalTime.MIN),
                LocalDateTime.of(LocalDate.parse(to), LocalTime.MAX));
    }
}