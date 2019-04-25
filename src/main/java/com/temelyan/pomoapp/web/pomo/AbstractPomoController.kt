package com.temelyan.pomoapp.web.pomo

import com.temelyan.pomoapp.model.Pomo
import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.PomoService
import com.temelyan.pomoapp.service.ProjectService
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

abstract class AbstractPomoController(
        private val authorisedUserService: AuthorisedUserService,
        private val projectService: ProjectService,
        private val pomoService: PomoService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    protected open fun add(length: Int, projectId: Int, clientTimeZone: Int) {
        logger.info("add Pomo with the length {} for user {}", length, authorisedUserService.getEmail())

        val serverTimeNow = LocalDateTime.now()

        val serverTimeZone = ZoneOffset.systemDefault()
                .rules
                .getOffset(serverTimeNow)
                .get(ChronoField.OFFSET_SECONDS) / 3600

        val clientsTime = serverTimeNow
                .truncatedTo(ChronoUnit.SECONDS)
                .plus((clientTimeZone - serverTimeZone).toLong(), ChronoUnit.HOURS)

        pomoService.add(Pomo(clientsTime, length), projectId, authorisedUserService.get().id())
    }

    protected open fun getUserWithPomosInDateRange(from: String, to: String): Set<Project> {
        logger.info("fetching pomos for dates between {} and {} for user {}", from, to, authorisedUserService.get())
        return projectService.getAllForUserWithTasksAndPomos(
                authorisedUserService.get().id(),
                LocalDateTime.of(LocalDate.parse(from), LocalTime.MIN),
                LocalDateTime.of(LocalDate.parse(to), LocalTime.MAX))
    }
}
