package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.User;

import java.util.List;

public interface UserRepopsitory {
    User save(User user);

    boolean delete(int id);

    User get(int id);

    User getByEmail(String email);

    List<User> getAll();

    User findUserByResetToken(String token);
}
