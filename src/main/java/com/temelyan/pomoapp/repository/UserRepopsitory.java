package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.User;

public interface UserRepopsitory {
    User save(User user);

    User get(int id);

    User getByEmail(String email);

    User findUserByResetToken(String token);
}
