package com.temelyan.pomoapp.repository;

import com.temelyan.pomoapp.model.User;

import java.time.LocalDateTime;

public interface UserRepopsitory {
    User save(User user);

    User get(int id);

    User getWithProjects(int id);

    User getByEmail(String email);

    User findUserByResetToken(String token);

    User getByIdWithPomosInDateRange(int userId, LocalDateTime from, LocalDateTime to);
}
