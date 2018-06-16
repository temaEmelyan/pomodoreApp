package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.util.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DataJpaUserRepositoryImpl implements UserRepopsitory {
    private final CrudUserRepository crudRepository;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataJpaUserRepositoryImpl(CrudUserRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    public User save(User user) {
        logger.info("Saving user {} to the database", user);
        return crudRepository.save(user);
    }

    @Override
    public User get(int id) {
        return crudRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with this id does not exist"));
    }

    @Override
    public User getByEmail(String email) {
        return crudRepository.getByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with this email does not exist"));
    }

    @Override
    public User findUserByResetToken(String token) {
        return crudRepository.findByResetToken(token).orElseThrow(RuntimeException::new);
    }
}
