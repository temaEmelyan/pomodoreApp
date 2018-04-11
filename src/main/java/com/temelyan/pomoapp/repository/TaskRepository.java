package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    Task save(Task task, int projectId);

    List<Task> getAllForProject(int projectId);
}
