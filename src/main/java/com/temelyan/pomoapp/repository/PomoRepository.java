package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Pomo;

public interface PomoRepository {
    Pomo save(Pomo pomo, int userId);
}
