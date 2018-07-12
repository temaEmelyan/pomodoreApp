package com.temelyan.pomoapp.repository.dataJpa;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.repository.PomoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public class DataJpaPomoRepositoryImpl implements PomoRepository {

    private final CrudPomoRepository crudPomoRepository;
    private final CrudTaskRepository crudTaskRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public DataJpaPomoRepositoryImpl(CrudPomoRepository crudPomoRepository,
                                     CrudTaskRepository crudTaskRepository) {
        this.crudPomoRepository = crudPomoRepository;
        this.crudTaskRepository = crudTaskRepository;
    }

    @Override
    public Pomo save(Pomo pomo, int taskId) {
        logger.info("save", pomo, taskId);
        Task one = crudTaskRepository.getOne(taskId);
        pomo.setTask(one);
        return crudPomoRepository.save(pomo);
    }

    @Override
    public List<Pomo> getAllForUserInDateRange(LocalDate fromDate, LocalDate toDate, int userId) {
        logger.info("getAllForUserInDateRange", toDate, userId);
        LocalDateTime fromDT = LocalDateTime.of(fromDate, LocalTime.MIN);
        LocalDateTime toDT = LocalDateTime.of(toDate, LocalTime.MAX);

        return crudPomoRepository.getAllForUserInDateRange(fromDT, toDT, userId);
    }
}