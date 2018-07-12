package com.temelyan.pomoapp;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;

import static java.util.Objects.requireNonNull;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public AuthorizedUser(User user) {
        super(user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
        this.user = user;
    }

    public static AuthorizedUser safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof AuthorizedUser) ? (AuthorizedUser) principal : null;
    }

    public static AuthorizedUser get() {
        AuthorizedUser user = safeGet();
        requireNonNull(user, "No authorized user found");
        return user;
    }

    public static Integer id() {
        return get().getUser().getId();
    }

    public void update(UserTo user) {
        this.user = new User(user);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return user.toString();
    }
}