package com.temelyan.pomoapp.Util;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;

public class UserUtil {
    public static UserTo asTo(User user) {
        return new UserTo(user.getId(), user.getEmail(), user.getPassword());
    }
}
