package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.to.ProjectTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public List<ProjectTo> getAll(int userId) {
        return projectRepository.getAll(userId).stream().map(ProjectTo::fromProject).collect(Collectors.toList());
    }
}
