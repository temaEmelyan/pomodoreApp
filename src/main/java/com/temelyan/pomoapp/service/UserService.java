package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    void update(UserTo userTo);

    User get(int id);

    void create(User user);

    List<User> getAll();

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User loadByEmail(String email);
}
