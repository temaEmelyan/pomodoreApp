package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudPomoRepository extends JpaRepository<Pomo, Integer> {
}
