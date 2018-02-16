package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PomoServiceImpl implements PomoService {
    final PomoRepository pomoRepository;

    @Autowired
    public PomoServiceImpl(PomoRepository pomoRepository) {
        this.pomoRepository = pomoRepository;
    }

    @Override
    public void add(Pomo pomo, int projectId) {
        pomoRepository.save(pomo, projectId);
    }

    @Override
    public List<Pomo> getAll(int userId) {
        return pomoRepository.getAll(userId);
    }
}
