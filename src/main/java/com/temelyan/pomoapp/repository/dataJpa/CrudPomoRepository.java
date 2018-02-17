package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudPomoRepository extends JpaRepository<Pomo, Integer> {

    @Query("SELECT p FROM Pomo p WHERE p.project.id=:projectId ORDER BY p.finish DESC")
    List<Pomo> getAll(@Param("projectId") int projectId);

    @Query("SELECT p FROM Pomo p WHERE p.project.user.id=:userId ORDER BY p.project.name, p.finish DESC")
    List<Pomo> getAllForUser(@Param("userId") int userId);

    @Query("SELECT p FROM Pomo p WHERE (p.project.user.id=:userId) " +
            "AND (p.finish BETWEEN :from_date AND :to) ORDER BY p.project.name, p.finish DESC")
    List<Pomo> getAllForUserInDateRange(@Param("from_date") LocalDateTime fromDate,
                                        @Param("to") LocalDateTime toDate,
                                        @Param("userId") int userId);
}
