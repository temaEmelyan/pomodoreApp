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

    @Query("SELECT p FROM Pomo p WHERE (p.task.project.user.id=:userId) " +
            "AND (p.finish BETWEEN :from_date AND :to) ORDER BY p.task.project.name, p.finish DESC")
    List<Pomo> getAllForUserInDateRange(@Param("from_date") LocalDateTime fromDate,
                                        @Param("to") LocalDateTime toDate,
                                        @Param("userId") int userId);
}
