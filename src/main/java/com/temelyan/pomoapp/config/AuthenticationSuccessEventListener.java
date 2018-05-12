package com.temelyan.pomoapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Event listener listening for any successful authentication events.
 * onApplicationEvent method gets triggered
 */
@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final HttpServletRequest httpRequest;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public AuthenticationSuccessEventListener(HttpServletRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent e) {
        HttpSession session = httpRequest.getSession();
        int interval = 60 * 60 * 24 * 3;//3 days
        session.setMaxInactiveInterval(interval);
        logger.info("User {} authenticated; session {}",
                e.getAuthentication().getName(),
                session.getId());
    }
}