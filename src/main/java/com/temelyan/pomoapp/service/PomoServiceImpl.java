package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PomoServiceImpl implements PomoService {
    private final PomoRepository pomoRepository;

    @Autowired
    public PomoServiceImpl(PomoRepository pomoRepository) {
        this.pomoRepository = pomoRepository;
    }

    @Override
    public void add(Pomo pomo, int taskId) {
        pomoRepository.save(pomo, taskId);
    }

    @Override
    public List<Pomo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int userId) {
        return pomoRepository.getAllForUserInDateRange(fromDate, toDate, userId);
    }
}
