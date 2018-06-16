package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "tasks", key = "#projectId"),
            @CacheEvict(value = "projectsWithTasks", key = "#userId")})
    public void save(Task task, int projectId, int userId) {
        taskRepository.save(task, projectId);
    }

    @Override
    @Cacheable(value = "tasks", key = "#projectId")
    public List<Task> getAllForProject(int projectId) {
        return taskRepository.getAllForProject(projectId);
    }
}
