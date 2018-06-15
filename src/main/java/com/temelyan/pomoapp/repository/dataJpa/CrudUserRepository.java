package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
    Optional<User> getByEmail(String email);

    @Override
    Optional<User> findById(Integer id);

    @Override
    @Transactional
    User save(User user);

    Optional<User> findByResetToken(String resetToken);
}
