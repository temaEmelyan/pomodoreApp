package com.temelyan.pomoapp.validator

import com.temelyan.pomoapp.service.AuthorisedUserService
import com.temelyan.pomoapp.service.UserService
import com.temelyan.pomoapp.to.UserTo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.validation.Errors
import org.springframework.validation.ValidationUtils
import org.springframework.validation.Validator

@Component
class UserUpdateValidator @Autowired
constructor(private val userService: UserService,
            private val passwordEncoder: PasswordEncoder,
            private val authorisedUserService: AuthorisedUserService) : Validator {

    override fun supports(clazz: Class<*>): Boolean {
        return clazz == UserTo::class.java
    }

    override fun validate(o: Any, errors: Errors) {
        val userTo = o as UserTo

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", "NotEmpty")
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty")

        if (userTo.password!!.length < 8 || userTo.password!!.length > 256) {
            errors.rejectValue("password", "Size.userForm.password")
        }
        if (userTo.passwordConfirm != userTo.newPassword) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm")
        }

        val user = userService.loadByEmail(authorisedUserService.get().userTo.email!!)

        if (!passwordEncoder.matches(userTo.password, user.password)) {
            errors.rejectValue("password", "Diff.userForm.originalPassword")
        }
    }
}
