package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepopsitory {
    private final CrudUserRepository crudRepository;

    @Autowired
    public DataJpaUserRepositoryImpl(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User save(User user) {
        return crudRepository.save(user);
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email).orElseThrow(RuntimeException::new);
    }

    @Override
    public User findUserByResetToken(String token) {
        return crudRepository.findByResetToken(token).orElseThrow(RuntimeException::new);
    }
}
