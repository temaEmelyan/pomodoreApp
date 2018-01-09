package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;

import java.util.List;

public interface PomoService {

    void add(Pomo pomo, int userId);

    List<Pomo> getAll(int userId);
}
