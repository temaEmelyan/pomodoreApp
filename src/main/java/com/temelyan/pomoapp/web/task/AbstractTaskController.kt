package com.temelyan.pomoapp.web.task

import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.TaskService
import org.slf4j.LoggerFactory

abstract class AbstractTaskController(
        private val taskService: TaskService,
        private val authorisedUserService: AuthorisedUserService) {
    private val logger = LoggerFactory.getLogger(javaClass)!!

    internal fun create(task: Task, projectId: Int) {
        logger.info("creating new task {} for project with id {} for user {}",
                task,
                projectId,
                authorisedUserService.get())

        taskService.save(task, projectId, authorisedUserService.get().id())
    }

    internal fun getAll(projectId: Int): List<Task> {
        logger.info("fetching all tasks for project with id {} for user {}",
                projectId,
                authorisedUserService.get())

        return taskService.getAllForProject(projectId)
    }
}
