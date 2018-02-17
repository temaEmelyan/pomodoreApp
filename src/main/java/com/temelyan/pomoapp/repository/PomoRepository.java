package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Pomo;

import java.util.List;

public interface PomoRepository {
    Pomo save(Pomo pomo, int projectId);

    List<Pomo> getAll(int projectId);

    List<Pomo> getAllForUser(int projectId);
}
