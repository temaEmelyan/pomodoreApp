package com.temelyan.pomoapp.web.project

import com.fasterxml.jackson.annotation.JsonView
import com.temelyan.pomoapp.JsonViews.ProjectViews
import com.temelyan.pomoapp.model.Project
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/project/")
class ProjectRestController : AbstractProjectController() {

    @JsonView(ProjectViews.IncludeTasks::class)
    @GetMapping("get")
    fun getAllProjects(): Set<Project> {
        return super.all
    }

    @PostMapping(path = ["add"])
    fun addProject(name: String) {
        val project = Project(name)
        super.create(project)
    }
}
