package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Pomo
import com.temelyan.pomoapp.repository.PomoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Repository
class DataJpaPomoRepositoryImpl @Autowired
constructor(private val crudPomoRepository: CrudPomoRepository, private val crudTaskRepository: CrudTaskRepository) : PomoRepository {

    override fun save(pomo: Pomo, taskId: Int): Pomo {
        val one = crudTaskRepository.getOne(taskId)
        pomo.task = one
        return crudPomoRepository.save(pomo)
    }

    override fun getAllForUserInDateRange(fromDate: LocalDate, toDate: LocalDate, userId: Int): List<Pomo> {
        val fromDT = LocalDateTime.of(fromDate, LocalTime.MIN)
        val toDT = LocalDateTime.of(toDate, LocalTime.MAX)

        return crudPomoRepository.getAllForUserInDateRange(fromDT, toDT, userId)
    }
}
