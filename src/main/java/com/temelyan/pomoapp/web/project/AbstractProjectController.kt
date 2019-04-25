package com.temelyan.pomoapp.web.project

import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.ProjectService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

abstract class AbstractProjectController {
    protected val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    private val projectService: ProjectService? = null
    @Autowired
    private val authorisedUserService: AuthorisedUserService? = null

    internal val all: Set<Project>
        get() {
            logger.info("fetching all projects for user {}", authorisedUserService!!.get())
            return projectService!!.getAllForUserWithTasks(authorisedUserService.get().id())
        }

    internal fun create(project: Project) {
        logger.info("creating new project {} for user {}", project, authorisedUserService!!.get())
        projectService!!.save(project, authorisedUserService.get().id())
    }
}
