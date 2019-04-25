package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Project
import com.temelyan.pomoapp.repository.ProjectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

import java.time.LocalDateTime

@Repository
class DataJpaProjectRepositoryImpl @Autowired
constructor(private val crudProjectRepository: CrudProjectRepository, private val crudUserRepository: CrudUserRepository) : ProjectRepository {

    override fun save(project: Project, userId: Int): Project {
        project.user = crudUserRepository.getOne(userId)
        return crudProjectRepository.save(project)
    }

    override fun getAllForUserWithTasks(userId: Int): Set<Project> {
        return crudProjectRepository.findAllByUserIdFetchWithTasks(userId)
    }

    override fun getAllForUserWithTasksAndPomos(userId: Int, from: LocalDateTime, to: LocalDateTime): Set<Project> {
        return crudProjectRepository.findAllByUserIdFetchWithTasksAndPomosInDateRange(userId, from, to)
    }
}
