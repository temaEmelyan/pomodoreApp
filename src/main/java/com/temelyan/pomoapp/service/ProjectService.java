package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;

import java.time.LocalDateTime;
import java.util.Set;

public interface ProjectService {
    Project save(Project project, int userId);

    Set<Project> getAllForUserWithTasks(int userId);

    Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDateTime from, LocalDateTime to);
}
