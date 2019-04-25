package com.temelyan.pomoapp.repository

import com.temelyan.pomoapp.model.User

interface UserRepopsitory {
    fun save(user: User): User

    fun get(id: Int): User

    fun getByEmail(email: String): User

    fun findUserByResetToken(token: String): User
}
