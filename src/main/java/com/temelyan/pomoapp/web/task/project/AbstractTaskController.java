package com.temelyan.pomoapp.web.task.project;

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

    void create(Task task, int projectId) {
        logger.info("creating new task {} for project with id {}", task, projectId);
        taskService.save(task, projectId);
    }

    List<Task> getAll(int projectId) {
        logger.info("fetching all tasks for project with id {}", projectId);
        return taskService.getAllForProject(projectId);
    }
}
