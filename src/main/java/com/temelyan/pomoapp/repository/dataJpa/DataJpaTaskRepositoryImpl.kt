package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class DataJpaTaskRepositoryImpl @Autowired
constructor(private val crudProjectRepository: CrudProjectRepository, private val crudRepository: CrudTaskRepository) : TaskRepository {

    override fun save(task: Task): Task {
        return crudRepository.save(task)
    }

    override fun save(task: Task, projectId: Int): Task {
        task.project = crudProjectRepository.getOne(projectId)
        return save(task)
    }

    override fun getAllForProject(projectId: Int): List<Task> {
        return crudRepository.getAllByProjectId(projectId)
    }
}
