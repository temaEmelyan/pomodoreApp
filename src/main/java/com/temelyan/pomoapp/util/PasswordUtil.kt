package com.temelyan.pomoapp.util

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils

import java.util.regex.Pattern

object PasswordUtil {
    private val BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}")

    fun encode(newPassword: String, passwordEncoder: PasswordEncoder): String? {
        if (!StringUtils.hasText(newPassword)) {
            return null
        }
        return if (isEncoded(newPassword)) {
            newPassword
        } else passwordEncoder.encode(newPassword)
    }

    private fun isEncoded(newPassword: String): Boolean {
        return BCRYPT_PATTERN.matcher(newPassword).matches()
    }
}
