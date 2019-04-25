package com.temelyan.pomoapp.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.temelyan.pomoapp.AuthorizedUser
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthenticationFilter internal constructor(filterUrl: String, authenticationManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {
    private val objectMapper = ObjectMapper()

    init {
        setAuthenticationManager(authenticationManager)
        setFilterProcessesUrl(filterUrl)
    }

    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse?): Authentication {
        val credentials = objectMapper.readValue<Map<String, String>>(
                req.inputStream, object : TypeReference<Map<String, String>>() {
        })

        return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                        credentials["username"],
                        credentials["password"],
                        ArrayList<GrantedAuthority>())
        )
    }

    override fun successfulAuthentication(req: HttpServletRequest,
                                          res: HttpServletResponse,
                                          chain: FilterChain,
                                          auth: Authentication) {

        val authorizedUser = auth.principal as AuthorizedUser
        val token = JWT.create()
                .withSubject(authorizedUser.username)
                .withClaim("user_id", authorizedUser.id())
                .withExpiresAt(Date(System.currentTimeMillis() + 864000000))
                .sign(Algorithm.HMAC512("I love bytes".toByteArray()))

        res.addHeader(HttpHeaders.AUTHORIZATION, "Bearer $token")

        res.writer.write(
                objectMapper.writeValueAsString(Collections.singletonMap("token", token))
        )
        res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
    }
}
