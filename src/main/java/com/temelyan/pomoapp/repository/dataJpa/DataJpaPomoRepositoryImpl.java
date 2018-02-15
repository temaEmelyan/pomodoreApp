package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
    public List<Pomo> getAll(int userId) {
        return crudPomoRepository.getAll(userId);
    }
}