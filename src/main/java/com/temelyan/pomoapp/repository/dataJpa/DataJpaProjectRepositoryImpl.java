package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {
    private final CrudProjectRepository crudProjectRepository;
    private final CrudUserRepository crudUserRepository;

    @Autowired
    public DataJpaProjectRepositoryImpl(CrudProjectRepository crudProjectRepository, CrudUserRepository crudUserRepository) {
        this.crudProjectRepository = crudProjectRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Project save(Project project, int userId) {
        project.setUser(crudUserRepository.getOne(userId));
        return crudProjectRepository.save(project);
    }

    @Override
    public Set<Project> getAllForUserWithTasks(int userId) {
        return crudProjectRepository.findAllByUserIdFetchWithTasks(userId);
    }

    @Override
    public Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDateTime from, LocalDateTime to) {
        return crudProjectRepository.findAllByUserIdFetchWithTasksAndPomosInDateRange(userId, from, to);
    }
}