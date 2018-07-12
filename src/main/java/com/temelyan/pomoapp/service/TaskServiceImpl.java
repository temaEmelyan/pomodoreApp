package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectService projectService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, ProjectService projectService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "tasks", key = "#projectId"),
            @CacheEvict(value = "projectsWithTasks", key = "#userId")})
    public void save(String taskName, int projectId, int userId) {
        logger.info("save {} {} {}", taskName, projectId, userId);
        Project project = projectService.getByIdWithUser(projectId);
        if (project.getUser().getId() != userId) {
            throw new RuntimeException();
        }
        Task task = new Task(taskName, project);
        taskRepository.save(task);
    }

    @Override
    @Cacheable(value = "tasks", key = "#projectId")
    public List<Task> getAllForProject(int projectId, int userId) {
        logger.info("getAllForProject {} {}", projectId, userId);
        Project project = projectService.getByIdWithUser(projectId);
        if (project.getUser().getId() != userId) {
            throw new RuntimeException();
        }
        return taskRepository.getAllForProject(projectId);
    }
}
