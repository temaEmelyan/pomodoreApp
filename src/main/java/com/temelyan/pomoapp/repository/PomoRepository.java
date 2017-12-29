package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Pomo;

import java.util.List;

public interface PomoRepository {
    Pomo add(Pomo Pomo, int userId);

    Pomo get(int id, int userId);

    List<Pomo> getAll(int userId);
}
