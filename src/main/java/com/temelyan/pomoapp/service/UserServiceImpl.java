package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.temelyan.pomoapp.util.UserUtil.prepareToSave;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepopsitory userRepopsitory;

    @Autowired
    public UserServiceImpl(UserRepopsitory userRepopsitory) {
        this.userRepopsitory = userRepopsitory;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = loadByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    @Override
    public User loadByEmail(String email) {
        return userRepopsitory.getByEmail(email.toLowerCase());
    }

    //    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    @Override
    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        userRepopsitory.save(prepareToSave(UserUtil.updateFromTo(user, userTo)));
    }

    @Override
    public void create(User user) {
        userRepopsitory.save(prepareToSave(user));
    }

    @Override
    public User get(int id) {
        return userRepopsitory.get(id);
    }

    //    @Cacheable("users")
    @Override
    public List<User> getAll() {
        return userRepopsitory.getAll();
    }
}
