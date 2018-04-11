package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.Pomo;

import java.time.LocalDate;
import java.util.List;

public interface PomoRepository {
    Pomo save(Pomo pomo, int taskId);

    List<Pomo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int userId);
}
