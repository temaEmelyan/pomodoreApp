package com.temelyan.pomoapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Event listener class triggered when a session is created or destroyed
 */
@Configuration
public class CustomHttpSessionListener implements HttpSessionListener {
    private final HttpServletRequest request;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public CustomHttpSessionListener(HttpServletRequest request) {
        this.request = request;
    }

    /**
     * Bean responsible for registering of this Listener in the spring context.
     */
    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        return new ServletListenerRegistrationBean<>(this);
    }

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        String ipAddress = "na";
        if (request != null) {
            ipAddress = request.getHeader("X-FORWARDED-FOR");
            if (ipAddress == null) {
                ipAddress = request.getRemoteAddr();
            }
            event.getSession().setAttribute("ip", ipAddress);
        }

        int interval_15_mins = 60 * 15;
        event.getSession().setMaxInactiveInterval(interval_15_mins);
        logger.info("session {} created ip: {}",
                event.getSession().getId(),
                ipAddress
        );
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        String ipAddress = "na";
        if (event.getSession().getAttribute("ip") != null) {
            ipAddress = (String) event.getSession().getAttribute("ip");
        }

        logger.info("session {} destroyed. Session max inactive interval was {}, session was accessed last @{}, ip: {}",
                event.getSession().getId(),
                event.getSession().getMaxInactiveInterval(),
                LocalDateTime.ofEpochSecond(
                        event.getSession().getLastAccessedTime() / 1_000,
                        0,
                        ZoneOffset.UTC)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                        .replace("T", " "),
                ipAddress
        );
    }
}
