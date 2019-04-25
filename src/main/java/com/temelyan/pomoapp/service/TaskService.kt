package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Task

interface TaskService {
    fun save(task: Task, projectId: Int, userId: Int)

    fun getAllForProject(projectId: Int): List<Task>
}
