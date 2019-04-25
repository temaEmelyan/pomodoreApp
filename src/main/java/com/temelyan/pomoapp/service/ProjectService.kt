package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Project

import java.time.LocalDateTime

interface ProjectService {
    fun save(project: Project, userId: Int): Project

    fun getAllForUserWithTasks(userId: Int): Set<Project>

    fun getAllForUserWithTasksAndPomos(userId: Int, from: LocalDateTime, to: LocalDateTime): Set<Project>
}
