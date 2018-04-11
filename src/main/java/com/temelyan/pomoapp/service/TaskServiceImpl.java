package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void add(String name, int projectId) {
        taskRepository.add(name, projectId);
    }

    @Override
    public List<Task> getAllForProject(int projectId) {
        return taskRepository.getAllForProject(projectId);
    }
}
