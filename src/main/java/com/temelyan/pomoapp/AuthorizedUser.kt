package com.temelyan.pomoapp

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.to.UserTo
import com.temelyan.pomoapp.util.UserUtil
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

class AuthorizedUser(user: User) :
        org.springframework.security.core.userdetails.User(
                user.email, user.password, true, true, true,
                true, ArrayList<GrantedAuthority>()
        ) {

    val userTo: UserTo = UserUtil.asTo(user)

    fun id(): Int {
        val id = userTo.getId()
        if (id == null) {
            SecurityContextHolder.getContext().authentication = null
            throw RuntimeException()
        }
        return id
    }

    override fun toString(): String {
        return userTo.toString()
    }

}
