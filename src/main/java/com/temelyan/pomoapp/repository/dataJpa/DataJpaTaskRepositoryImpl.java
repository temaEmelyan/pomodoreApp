package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaTaskRepositoryImpl implements TaskRepository {

    private final CrudTaskRepository crudRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataJpaTaskRepositoryImpl(CrudTaskRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public Task save(Task task) {
        logger.info("save {}", task);
        return crudRepository.save(task);
    }

    @Override
    public List<Task> getAllForProject(int projectId) {
        logger.info("getAllForProject {}", projectId);
        return crudRepository.getAllByProjectId(projectId);
    }
}