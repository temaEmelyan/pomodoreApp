package com.temelyan.pomoapp.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.temelyan.pomoapp.AuthorizedUser
import com.temelyan.pomoapp.model.User
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JWTAuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(req: HttpServletRequest,
                                  res: HttpServletResponse,
                                  chain: FilterChain) {
        val header = req.getHeader(HttpHeaders.AUTHORIZATION)

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res)
            return
        }

        val authentication = getAuthentication(req)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(req, res)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (token != null) {
            // parse the token.
            val decodedJWT = JWT.require(Algorithm.HMAC512("I love bytes".toByteArray()))
                    .build().verify(token.replace("Bearer ", ""))

            val email = decodedJWT.subject
            val userId = decodedJWT.getClaim("user_id").asInt()

            val user = User(userId, email, "")

            if (userId != null) {
                return UsernamePasswordAuthenticationToken(AuthorizedUser(user), null, ArrayList<GrantedAuthority>())
            }
        }
        return null
    }
}
