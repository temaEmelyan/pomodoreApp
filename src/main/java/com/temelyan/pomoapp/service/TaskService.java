package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskService {
    void save(Task task, int projectId);

    List<Task> getAllForProject(int projectId);
}
