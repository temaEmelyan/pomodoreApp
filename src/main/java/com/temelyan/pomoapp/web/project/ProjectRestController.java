package com.temelyan.pomoapp.web.project;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.to.ProjectTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectRestController extends AbstractProjectController {

    @PostMapping("/ajax/add/project")
    public void addProject(Project project) {
        super.create(project);
    }

    @GetMapping(value = "/ajax/get/projects", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectTo> getAllProjects() {
        return super.getAll();
    }
}
