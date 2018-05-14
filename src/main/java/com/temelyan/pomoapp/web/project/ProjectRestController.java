package com.temelyan.pomoapp.web.project;

import com.fasterxml.jackson.annotation.JsonView;
import com.temelyan.pomoapp.JsonViews.ProjectViews;
import com.temelyan.pomoapp.model.Project;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/ajax/project/")
public class ProjectRestController extends AbstractProjectController {

    @PostMapping(path = "add")
    public void addProject(String name) {
        Project project = new Project(name);
        super.create(project);
    }

    @JsonView(ProjectViews.IncludeTasks.class)
    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Project> getAllProjects() {
        return super.getAll();
    }
}
