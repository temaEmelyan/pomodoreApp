package com.temelyan.pomoapp.web.project;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class AbstractProjectController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectService projectService;

    void create(Project project) {
        logger.info("creating new project {} for user {}", project, AuthorizedUser.get());
        projectService.save(project, AuthorizedUser.id());
    }

    Set<Project> getAll() {
        logger.info("fetching all projects for user {}", AuthorizedUser.get());
        return projectService.getAllForUserWithTasks(AuthorizedUser.id());
    }
}
