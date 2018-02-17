package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.to.PomoTo;

import java.util.List;

public interface PomoService {

    void add(Pomo pomo, int projectId);

    List<PomoTo> getAll(int projectId);

    List<PomoTo> getAllForUser(int userId);
}
