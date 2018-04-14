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

    @Query("select p from Pomo p " +
            "join fetch p.task t " +
            "join fetch t.project pr " +
            "join fetch pr.user u " +
            "where u.id=:user_id " +
            "and (p.finish between :from_date and :to_date)" +
            "order by p.task.project.name, p.task.name, p.finish DESC")
    List<Pomo> getAllForUserInDateRange(@Param("from_date") LocalDateTime fromDate,
                                        @Param("to_date") LocalDateTime toDate,
                                        @Param("user_id") int userId);
}
