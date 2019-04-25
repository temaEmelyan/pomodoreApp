package com.temelyan.pomoapp.to

import com.temelyan.pomoapp.model.Project

import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size
import java.io.Serializable
import java.util.Collections

class UserTo : BaseTo, Serializable {

    @Email
    @NotBlank
    var email: String? = null

    @Size(min = 8, max = 32, message = "length must between 8 and 32 characters")
    var password: String? = null
    var newPassword: String? = null
    var passwordConfirm: String? = null
    var projects: Set<Project>? = null

    constructor() {}

    @JvmOverloads
    constructor(id: Int?, email: String, password: String, projects: Set<Project>? = null) : super(id) {
        this.email = email
        this.password = password
        this.projects = projects ?: emptySet()
    }

    override fun toString(): String {
        return "UserTo{" +
                "id=" + getId() +
                ", email='" + email + '\''.toString() +
                '}'.toString()
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
