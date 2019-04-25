package com.temelyan.pomoapp.repository

import com.temelyan.pomoapp.model.Pomo

import java.time.LocalDate

interface PomoRepository {
    fun save(pomo: Pomo, taskId: Int): Pomo

    fun getAllForUserInDateRange(fromDate: LocalDate, toDate: LocalDate, userId: Int): List<Pomo>
}
