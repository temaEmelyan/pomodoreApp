package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskService {
    void save(String task, int projectId, int userId);

    List<Task> getAllForProject(int projectId, int userId);
}
