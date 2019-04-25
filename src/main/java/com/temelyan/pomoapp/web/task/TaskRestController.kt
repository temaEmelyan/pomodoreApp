package com.temelyan.pomoapp.web.task

import com.fasterxml.jackson.annotation.JsonView
import com.temelyan.pomoapp.JsonViews.TaskViews
import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.TaskService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/task/")
class TaskRestController(authorisedUserService: AuthorisedUserService, taskService: TaskService)
    : AbstractTaskController(taskService, authorisedUserService) {

    @PostMapping("add")
    fun addTask(name: String, projectId: Int) {
        super.create(Task(name), projectId)
    }

    @JsonView(TaskViews.Default::class)
    @GetMapping(path = ["get"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllTasks(projectId: Int): List<Task> {
        return super.getAll(projectId)
    }
}
