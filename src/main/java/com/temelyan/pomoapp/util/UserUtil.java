package com.temelyan.pomoapp.util;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;

public class UserUtil {
    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getEmail(), user.getPassword(), user.getProjects());
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User createNewFromTo(UserTo newUser) {
        return new User(null, newUser.getEmail().toLowerCase(), newUser.getPassword());
    }

    public static User prepareToSave(User user) {
        user.setPassword(PasswordUtil.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
