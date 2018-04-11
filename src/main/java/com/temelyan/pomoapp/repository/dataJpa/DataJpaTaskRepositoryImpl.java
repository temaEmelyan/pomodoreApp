package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaTaskRepositoryImpl implements TaskRepository {

    private final CrudTaskRepository crudRepository;
    private final CrudProjectRepository crudProjectRepository;

    @Autowired
    public DataJpaTaskRepositoryImpl(CrudProjectRepository crudProjectRepository, CrudTaskRepository crudRepository) {
        this.crudProjectRepository = crudProjectRepository;
        this.crudRepository = crudRepository;
    }

    @Override
    public void add(String name, int projectId) {
        Task task = new Task(name);
        task.setProject(crudProjectRepository.getOne(projectId));
        crudRepository.save(task);
    }

    @Override
    public List<Task> getAllForProject(int projectId) {
        return crudRepository.getAllByProjectId(projectId);
    }
}