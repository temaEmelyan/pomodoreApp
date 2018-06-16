package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PomoServiceImpl implements PomoService {
    private final PomoRepository pomoRepository;

    @Autowired
    public PomoServiceImpl(PomoRepository pomoRepository) {
        this.pomoRepository = pomoRepository;
    }

    @Override
    public void add(Pomo pomo, int taskId, int userId) {
        pomoRepository.save(pomo, taskId);
    }
}
