package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    List<Task> getAllForProject(int projectId);
}
