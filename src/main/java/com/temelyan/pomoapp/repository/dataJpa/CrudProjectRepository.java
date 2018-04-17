package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CrudProjectRepository extends JpaRepository<Project, Integer> {
    Set<Project> findAllByUserIdOrderByName(int userId);
}
