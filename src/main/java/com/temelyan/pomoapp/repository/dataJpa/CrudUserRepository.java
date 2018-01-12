package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    User getByEmail(String email);

    Optional<User> findById(Integer id);

    @Override
    @Transactional
    User save(User user);

    @Override
    List<User> findAll(Sort sort);
}
