package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {
    private final CrudProjectRepository crudProjectRepository;

    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaProjectRepositoryImpl(
            CrudProjectRepository crudProjectRepository,
            CrudUserRepository crudUserRepository
    ) {
        this.crudProjectRepository = crudProjectRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public List<Project> getAll(Integer userId) {
        return crudProjectRepository.findAllByUserOrderByName(crudUserRepository.getOne(userId));
    }

    @Override
    public Project save(Project project) {
        return crudProjectRepository.save(project);
    }
}