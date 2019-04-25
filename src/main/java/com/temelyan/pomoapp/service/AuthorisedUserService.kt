package com.temelyan.pomoapp.service

import com.temelyan.pomoapp.AuthorizedUser
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class AuthorisedUserService {
    fun get(): AuthorizedUser {
        val principal = SecurityContextHolder.getContext().authentication.principal
        if (principal is AuthorizedUser) {
            return principal
        }
        throw RuntimeException("wrong principal")
    }

    fun getEmail(): String {
        return get().userTo.email!!
    }
}
