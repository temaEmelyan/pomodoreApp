package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Project;

import java.util.Set;

public interface ProjectRepository {
    Project save(Project project);

    Project save(Project project, Integer userId);

    Set<Project> getAllForUser(Integer userId);
}
