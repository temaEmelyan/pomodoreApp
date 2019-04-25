package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.AuthorizedUser
import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.to.UserTo
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

interface UserService : UserDetailsService {
    fun update(userTo: UserTo): User

    fun update(user: User): User

    fun create(user: User): User

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): AuthorizedUser

    fun loadByEmail(email: String): User

    fun findUserByResetToken(token: String): User
}
