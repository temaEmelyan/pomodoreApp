package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaPomoRepositoryImpl implements PomoRepository {

    @Autowired
    private CrudUserRepository crudUserRepository;

    @Autowired
    private CrudPomoRepository crudPomoRepository;

    @Override
    public Pomo save(Pomo pomo, int userId) {
        User one = crudUserRepository.getOne(userId);
        pomo.setUser(one);
        return crudPomoRepository.save(pomo);
    }
}