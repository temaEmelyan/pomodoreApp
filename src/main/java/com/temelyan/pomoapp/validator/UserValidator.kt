package com.temelyan.pomoapp.validator

import com.temelyan.pomoapp.model.User
import com.temelyan.pomoapp.service.UserService
import com.temelyan.pomoapp.to.UserTo
import com.temelyan.pomoapp.util.exception.NotFoundException
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator
import java.util.*

@Component
class UserValidator(private val userService: UserService) : Validator {

    override fun supports(aClass: Class<*>): Boolean {
        return UserTo::class.java == aClass
    }

    override fun validate(o: Any, errors: Errors) {
        val userTo = o as UserTo

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty")

        var user = Optional.empty<User>()
        try {
            user = Optional.of(userService.loadByEmail(userTo.email!!))
        } catch (ignored: NotFoundException) {
        }

        if (user.isPresent) {
            errors.rejectValue("email", "Duplicate.userForm.email")
        }
        if (userTo.password!!.length < 8 || userTo.password!!.length > 256) {
            errors.rejectValue("password", "Size.userForm.password")
        }
        if (userTo.passwordConfirm != userTo.password) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }
    }
}
