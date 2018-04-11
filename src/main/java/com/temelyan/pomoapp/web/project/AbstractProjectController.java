package com.temelyan.pomoapp.web.project;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class AbstractProjectController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectService projectService;

    void create(Project project) {
        project.setUser(new User(AuthorizedUser.get().getUserTo()));
        logger.info("creating new project {} for User with id", project, project.getUser().getId());
        projectService.save(project);
    }

    List<Project> getAll() {
        logger.info("fetching all projects for user {}", AuthorizedUser.id());
        return projectService.getAllForUser(AuthorizedUser.id());
    }
}
