package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserService {
    void update(UserTo userTo);

    User get(int id);

    void create(User user);

    List<User> getAll();
}
