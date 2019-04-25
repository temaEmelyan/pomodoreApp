package com.temelyan.pomoapp.web.user

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.UserService
import com.temelyan.pomoapp.to.UserTo
import com.temelyan.pomoapp.util.ValidationUtil.assureIdConsistent
import com.temelyan.pomoapp.util.ValidationUtil.checkNew
import org.slf4j.LoggerFactory
import javax.validation.Valid

abstract class AbstractUserController(
        var userService: UserService,
        var authorisedUserService: AuthorisedUserService) {
    protected val logger = LoggerFactory.getLogger(javaClass)


    protected fun create(user: User) {
        logger.info("create {}", user)
        checkNew(user)
        userService.create(user)
    }

    protected fun update(@Valid userTo: UserTo, id: Int) {
        logger.info("update {} with id={}", userTo, id)
        assureIdConsistent(userTo, authorisedUserService.get().id())
        val updatedUser = userService.update(userTo)
        authorisedUserService.get().userTo.password = updatedUser.password
    }
}
