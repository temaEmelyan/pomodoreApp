package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.model.Pomo
import com.temelyan.pomoapp.repository.PomoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PomoServiceImpl @Autowired
constructor(private val pomoRepository: PomoRepository) : PomoService {

    override fun add(pomo: Pomo, taskId: Int, userId: Int) {
        pomoRepository.save(pomo, taskId)
    }
}
