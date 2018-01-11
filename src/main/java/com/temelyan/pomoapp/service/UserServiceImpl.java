package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.Util.UserUtil;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.to.UserTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        userRepopsitory.save(UserUtil.updateFromTo(user, userTo));
    }

    @Override
    public void create(User user) {
        userRepopsitory.save(user);
    }

    @Override
    public User get(int id) {
        return userRepopsitory.get(id);
    }
}
