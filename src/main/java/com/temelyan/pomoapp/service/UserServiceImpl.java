package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserSevice, UserDetailsService {

    @Autowired
    UserRepopsitory userRepopsitory;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepopsitory.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
