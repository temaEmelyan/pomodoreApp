package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskService {
    void add(String name, int projectId);

    List<Task> getAllForProject(int projectId);
}
