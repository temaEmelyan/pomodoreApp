package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;

import java.util.List;

public interface ProjectService {
    Project save(Project project);

    List<Project> getAllForUser(int userId);
}
