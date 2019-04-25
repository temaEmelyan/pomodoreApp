package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Pomo

interface PomoService {
    fun add(pomo: Pomo, taskId: Int, userId: Int)
}
