package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findAllByUser(User user);
}
