package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudUserRepository extends JpaRepository<User, Integer> {
}
