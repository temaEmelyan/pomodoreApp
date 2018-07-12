package com.temelyan.pomoapp.web.task;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractTaskController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    void create(String taskName, int projectId) {
        logger.info("creating new task {} for project with id {} for user {}",
                taskName,
                projectId,
                AuthorizedUser.get());

        taskService.save(taskName, projectId, AuthorizedUser.id());
    }

    List<Task> getAll(int projectId) {
        logger.info("fetching all tasks for project with id {} for user {}",
                projectId,
                AuthorizedUser.get());

        return taskService.getAllForProject(projectId, AuthorizedUser.id());
    }
}
