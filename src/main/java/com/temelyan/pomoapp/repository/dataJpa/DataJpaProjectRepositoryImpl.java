package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {
    private final CrudProjectRepository crudProjectRepository;

    @Autowired
    public DataJpaProjectRepositoryImpl(CrudProjectRepository crudProjectRepository) {
        this.crudProjectRepository = crudProjectRepository;
    }

    @Override
    public Project save(Project project) {
        return crudProjectRepository.save(project);
    }

    @Override
    public List<Project> getAllForUser(Integer userId) {
        return crudProjectRepository.findAllByUserIdOrderByName(userId);
    }
}