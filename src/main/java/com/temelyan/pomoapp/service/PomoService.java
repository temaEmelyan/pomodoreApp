package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;

import java.time.LocalDate;
import java.util.List;

public interface PomoService {
    void add(Pomo pomo, int taskId);

    List<Pomo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int userId);
}
