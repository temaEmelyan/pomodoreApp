package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface CrudProjectRepository extends JpaRepository<Project, Integer> {
    @Query("select p from Project p " +
            "left join fetch p.tasks t " +
            "where p.user.id=:user_id")
    Set<Project> findAllByUserIdFetchWithTasks(@Param("user_id") int userId);

    @Query("select pr from Project pr " +
            "join fetch pr.tasks t " +
            "join fetch t.pomos p " +
            "where pr.user.id=:user_id and (p.finish between :from_date and :to_date)")
    Set<Project> findAllByUserIdFetchWithTasksAndPomosInDateRange(
            @Param("user_id") int userId,
            @Param("from_date") LocalDateTime from,
            @Param("to_date") LocalDateTime to
    );

    @Query("select p from Project p " +
            "left join fetch p.user u " +
            "where p.id = ?1")
    Optional<Project> findByIdFetchWithUser(int projectId);
}
