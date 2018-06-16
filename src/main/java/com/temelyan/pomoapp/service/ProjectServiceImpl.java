package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @CacheEvict(value = "projectsWithTasks", key = "#userId")
    public Project save(Project project, int userId) {
        return projectRepository.save(project, userId);
    }

    @Override
    @Cacheable(value = "projectsWithTasks", key = "#userId")
    public Set<Project> getAllForUserWithTasks(int userId) {
        return projectRepository.getAllForUserWithTasks(userId);
    }

    @Override
    public Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDateTime from, LocalDateTime to) {
        return projectRepository.getAllForUserWithTasksAndPomos(userId, from, to);
    }
}