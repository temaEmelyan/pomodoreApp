package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrudTaskRepository extends JpaRepository<Task, Integer> {
    List<Task> getAllByProjectId(int projectId);
}
