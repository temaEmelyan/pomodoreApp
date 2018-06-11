package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CrudProjectRepository extends JpaRepository<Project, Integer> {
    Set<Project> findAllByUserId(int userId);

    @Query("select p from Project p " +
            "left join fetch p.tasks t " +
            "where p.user.id=:user_id")
    Set<Project> findAllByUserIdFetchTasks(@Param("user_id") int userId);
}
