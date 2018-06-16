package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Project;

import java.time.LocalDateTime;
import java.util.Set;

public interface ProjectRepository {
    Project save(Project project, int userId);

    Set<Project> getAllForUserWithTasks(int userId);

    Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDateTime from, LocalDateTime to);
}
