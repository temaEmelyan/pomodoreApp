package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;

import java.util.Set;

public interface ProjectService {
    Project save(Project project);

    Project save(Project project, int userId);

    Set<Project> getAllForUser(int userId);
}
