package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Project;

import java.util.List;

public interface ProjectRepository {
    Project save(Project project);

    List<Project> getAllForUser(Integer userId);
}
