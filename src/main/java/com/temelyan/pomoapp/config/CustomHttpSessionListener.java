package com.temelyan.pomoapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Event listener class triggered when a session is created or destroyed
 */
@Configuration
public class CustomHttpSessionListener implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Bean responsible for registering of this Listener in the spring context.
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(this);
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("new session created {}", se.getSession());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        logger.info("session {} destroyed. Session max inactive interval was {}"
                , event.getSession()
                , event.getSession().getMaxInactiveInterval());
    }
}
