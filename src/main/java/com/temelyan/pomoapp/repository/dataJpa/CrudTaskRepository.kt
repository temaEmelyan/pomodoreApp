package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Task
import org.springframework.data.jpa.repository.JpaRepository

interface CrudTaskRepository : JpaRepository<Task, Int> {
    fun getAllByProjectId(projectId: Int): List<Task>
}
