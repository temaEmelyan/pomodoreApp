package com.temelyan.pomoapp.util

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.to.UserTo
import org.springframework.security.crypto.password.PasswordEncoder

object UserUtil {
    fun asTo(user: User): UserTo {
        return UserTo(user.getId(), user.email!!, user.password!!)
    }

    fun updateFromTo(user: User, userTo: UserTo): User {
        user.password = userTo.password
        return user
    }

    fun createNewFromTo(newUser: UserTo): User {
        return User(null, newUser.email!!.toLowerCase(), newUser.password!!)
    }

    fun prepareToSave(user: User, passwordEncoder: PasswordEncoder): User {
        user.password = PasswordUtil.encode(user.password!!, passwordEncoder)
        user.email = user.email!!.toLowerCase()
        return user
    }
}
