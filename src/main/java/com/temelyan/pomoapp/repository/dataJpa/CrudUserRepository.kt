package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface CrudUserRepository : JpaRepository<User, Int> {
    fun getByEmail(email: String): Optional<User>

    override fun findById(id: Int?): Optional<User>

    @Transactional
    fun save(user: User): User

    fun findByResetToken(resetToken: String): Optional<User>
}
