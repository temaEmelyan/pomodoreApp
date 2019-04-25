package com.temelyan.pomoapp.repository

import com.temelyan.pomoapp.model.Task

interface TaskRepository {
    fun save(task: Task): Task

    fun save(task: Task, projectId: Int): Task

    fun getAllForProject(projectId: Int): List<Task>
}
