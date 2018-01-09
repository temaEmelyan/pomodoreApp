package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudPomoRepository extends JpaRepository<Pomo, Integer> {

    @Query("SELECT p FROM Pomo p WHERE p.user.id=:userId ORDER BY p.finish DESC")
    List<Pomo> getAll(@Param("userId") int userId);
}
