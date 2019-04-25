package com.temelyan.pomoapp.model


import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.temelyan.pomoapp.to.UserTo
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(name = "USERS", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])], indexes = [Index(columnList = "id", unique = true), Index(columnList = "email", unique = true)])
@JsonIgnoreProperties(ignoreUnknown = true)
open class User : AbstractEntity {
    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    var email: String? = null

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 8)
    @JsonIgnore
    var password: String? = null

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    var projects: MutableSet<Project>? = null

    @Column(name = "reset_token")
    @JsonIgnore
    var resetToken: String? = null

    constructor()

    constructor(id: Int?, @Email @NotBlank email: String, @NotBlank @Size(min = 8) password: String) : super(id) {
        if (id != null) {
            setId(id)
        }
        this.email = email
        this.password = password
    }

    constructor(userTo: UserTo) : this(userTo.getId(), userTo.email!!, userTo.password!!)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        if (!super.equals(other)) return false
        val user = other as User?
        return email == user!!.email
    }

    override fun hashCode(): Int {
        return Objects.hash(super.hashCode(), email)
    }

    override fun toString(): String {
        return "User{" +
                "email='" + email + '\''.toString() +
                "} " + super.toString()
    }
}
