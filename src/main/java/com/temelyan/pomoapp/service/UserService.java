package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.to.UserTo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {
    User update(UserTo userTo);

    User update(User user);

    User create(User user);

    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    User loadByEmail(String email);

    User findUserByResetToken(String token);
}
