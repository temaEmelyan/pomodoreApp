package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    @CacheEvict(value = "projectsWithTasks", key = "#userId")
    public Project save(Project project, int userId) {
        logger.info("save {} {}", project, userId);
        return projectRepository.save(project, userId);
    }

    @Override
    @Cacheable(value = "projectsWithTasks", key = "#userId")
    public Set<Project> getAllForUserWithTasks(int userId) {
        logger.info("getAllForUserWithTasks {}", userId);
        return projectRepository.getAllForUserWithTasks(userId);
    }

    @Override
    public Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDate from, LocalDate to) {
        logger.info("getAllForUserWithTasksAndPomos {} {} {}", userId, from, to);
        return projectRepository.getAllForUserWithTasksAndPomos(userId, from, to);
    }

    @Override
    public Project getByIdWithUser(int projectId) {
        logger.info("getByIdWithUser {}", projectId);
        return projectRepository.getByIdWithUser(projectId);
    }
}