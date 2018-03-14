package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepopsitory {
    private static final Sort SORT_EMAIL = new Sort("email");

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
    public boolean delete(int id) {
        return false;
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id).orElse(null);
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email);
    }

    @Override
    public User findUserByResetToken(String token) {
        return crudRepository.findByResetToken(token);
    }

    @Override
    public List<User> getAll() {
        return crudRepository.findAll(SORT_EMAIL);
    }
}
