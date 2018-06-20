package com.temelyan.pomoapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder encoder;
    private final UserDetailsService userDetailsService;
    private final Environment environment;

    @Autowired
    public WebSecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService,
                             PasswordEncoder encoder, Environment environment) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
        this.environment = environment;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessDeniedHandler();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/css/**", "/js/**", "/webjars/**",
                        "/favicon.ico", "/manifest.json", "/images/**")
                .permitAll()
                .antMatchers("/registration", "/forgot", "/reset", "/login").anonymous()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                .formLogin().loginPage("/login").permitAll()
                .failureForwardUrl("/fail").permitAll()
                .and()
                .logout().permitAll();

        Boolean requireHTTPS = false;
        if (Arrays.asList(environment.getActiveProfiles()).contains("aws")) {
            requireHTTPS = true;
        }

        if (requireHTTPS) {
            http.requiresChannel().anyRequest().requiresSecure();
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
}