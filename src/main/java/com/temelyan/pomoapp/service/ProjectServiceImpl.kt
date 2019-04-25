package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

import java.time.LocalDateTime

@Service
class ProjectServiceImpl @Autowired
constructor(private val projectRepository: ProjectRepository) : ProjectService {

    @CacheEvict(value = ["projectsWithTasks"], key = "#userId")
    override fun save(project: Project, userId: Int): Project {
        return projectRepository.save(project, userId)
    }

    @Cacheable(value = ["projectsWithTasks"], key = "#userId")
    override fun getAllForUserWithTasks(userId: Int): Set<Project> {
        return projectRepository.getAllForUserWithTasks(userId)
    }

    override fun getAllForUserWithTasksAndPomos(userId: Int, from: LocalDateTime, to: LocalDateTime): Set<Project> {
        return projectRepository.getAllForUserWithTasksAndPomos(userId, from, to)
    }
}
