package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Task
import com.temelyan.pomoapp.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service

@Service
class TaskServiceImpl @Autowired
constructor(private val taskRepository: TaskRepository) : TaskService {

    @Caching(evict = [CacheEvict(value = ["tasks"], key = "#projectId"), CacheEvict(value = ["projectsWithTasks"], key = "#userId")])
    override fun save(task: Task, projectId: Int, userId: Int) {
        taskRepository.save(task, projectId)
    }

    @Cacheable(value = ["tasks"], key = "#projectId")
    override fun getAllForProject(projectId: Int): List<Task> {
        return taskRepository.getAllForProject(projectId)
    }
}
