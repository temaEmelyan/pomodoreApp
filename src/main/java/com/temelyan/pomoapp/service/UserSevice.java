package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;

public interface UserSevice {
    void update(UserTo userTo);

    User get(int id);

    void create(User user);
}
