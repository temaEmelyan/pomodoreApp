package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DataJpaPomoRepositoryImpl implements PomoRepository {

    private final CrudPomoRepository crudPomoRepository;

    private final CrudProjectRepository crudProjectRepository;

    @Autowired
    public DataJpaPomoRepositoryImpl(CrudPomoRepository crudPomoRepository, CrudProjectRepository crudProjectRepository) {
        this.crudPomoRepository = crudPomoRepository;
        this.crudProjectRepository = crudProjectRepository;
    }

    @Override
    public Pomo save(Pomo pomo, int projectId) {
        Project one = crudProjectRepository.getOne(projectId);
        pomo.setProject(one);
        return crudPomoRepository.save(pomo);
    }

    @Override
    public List<Pomo> getAll(int projectId) {
        return crudPomoRepository.getAll(projectId);
    }

    @Override
    public List<Pomo> getAllForUser(int userId) {
        return crudPomoRepository.getAllForUser(userId);
    }

    @Override
    public List<Pomo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int userId) {
        LocalDateTime fromDT = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime toDT = LocalDateTime.of(toDate, LocalTime.MAX);

        return crudPomoRepository.getAllForUserInDateRange(fromDT, toDT, userId);
    }
}