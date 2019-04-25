package com.temelyan.pomoapp.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class WebSecurity(private val userService: UserDetailsService, private val passwordEncoder: PasswordEncoder) {

    private val loginEndpoint = "/login"

    @Configuration
    @Order(1)
    inner class ApiSecurityAdapter : WebSecurityConfigurerAdapter() {

        @Throws(Exception::class)
        override fun configure(http: HttpSecurity) {
            http.antMatcher("/api/**")
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            http.antMatcher("/api/**")
                    .cors().and().csrf().disable().authorizeRequests()
                    .anyRequest().authenticated()
                    .and()
                    .addFilter(JWTAuthenticationFilter("/api$loginEndpoint", authenticationManager()))
                    .addFilter(JWTAuthorizationFilter(authenticationManager()))
                    .exceptionHandling().authenticationEntryPoint { _, httpServletResponse, _ ->
                        httpServletResponse.sendError(
                                HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.reasonPhrase
                        )
                    }
        }
    }


    @Configuration
    inner class WebSecurityAdapter : WebSecurityConfigurerAdapter() {

        override fun configure(http: HttpSecurity) {
            http
                    .authorizeRequests()
                    .antMatchers(loginEndpoint).permitAll()
                    .antMatchers(HttpMethod.POST, "/registration").anonymous()
                    .antMatchers(HttpMethod.GET, "/registration").anonymous()
                    .and().authorizeRequests().antMatchers(
                            loginEndpoint, "/css/**", "/js/**", "/webjars/**",
                            "/favicon.ico", "/manifest.json", "/images/**")
                    .permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin().loginPage(loginEndpoint).and()
                    .logout()
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .and()
                    .csrf()
        }

        public override fun configure(auth: AuthenticationManagerBuilder) {
            auth.userDetailsService(userService).passwordEncoder(passwordEncoder)
        }
    }

    @Bean
    internal fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}
