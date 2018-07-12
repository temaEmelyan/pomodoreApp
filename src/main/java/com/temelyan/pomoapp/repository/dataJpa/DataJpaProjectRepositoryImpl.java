package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Repository
public class DataJpaProjectRepositoryImpl implements ProjectRepository {
    private final CrudProjectRepository crudProjectRepository;
    private final CrudUserRepository crudUserRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataJpaProjectRepositoryImpl(CrudProjectRepository crudProjectRepository,
                                        CrudUserRepository crudUserRepository) {
        this.crudProjectRepository = crudProjectRepository;
        this.crudUserRepository = crudUserRepository;
    }

    @Override
    public Project save(Project project, int userId) {
        logger.info("save {}", project, userId);
        project.setUser(crudUserRepository.getOne(userId));
        return crudProjectRepository.save(project);
    }

    @Override
    public Set<Project> getAllForUserWithTasks(int userId) {
        logger.info("getAllForUserWithTasks {}", userId);
        return crudProjectRepository.findAllByUserIdFetchWithTasks(userId);
    }

    @Override
    public Set<Project> getAllForUserWithTasksAndPomos(int userId, LocalDate from, LocalDate to) {
        logger.info("getAllForUserWithTasksAndPomos {} {} {}", userId, from, to);
        LocalDateTime fromDT = LocalDateTime.of(from, LocalTime.MIN);
        LocalDateTime toDT = LocalDateTime.of(to, LocalTime.MAX);
        return crudProjectRepository.findAllByUserIdFetchWithTasksAndPomosInDateRange(userId, fromDT, toDT);
    }

    @Override
    public Project getByIdWithUser(int projectId) {
        logger.info("getByIdWithUser {} {} {}", projectId);
        return crudProjectRepository.findByIdFetchWithUser(projectId).orElseThrow(RuntimeException::new);
    }
}