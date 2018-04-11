package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project save(Project project);

    Project save(Project project, Integer userId);

    List<Project> getAllForUser(Integer userId);
}
