package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskRepository {
    void add(String name, int projectId);

    List<Task> getAllForProject(int projectId);
}
