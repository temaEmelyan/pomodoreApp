package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PomoServiceImpl implements PomoService {
    private final PomoRepository pomoRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public PomoServiceImpl(PomoRepository pomoRepository) {
        this.pomoRepository = pomoRepository;
    }

    @Override
    public void add(Pomo pomo, int taskId, int userId) {
        logger.info("add {} {} {}", pomo, taskId, userId);
        pomoRepository.save(pomo, taskId);
    }
}
