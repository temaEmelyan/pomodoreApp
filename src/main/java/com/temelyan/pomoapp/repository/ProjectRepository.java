package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Project;

import java.util.List;

public interface ProjectRepository {
    List<Project> getAll(Integer userId);

    Project save(Project project);
}
