package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.temelyan.pomoapp.util.UserUtil.prepareToSave;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserRepopsitory userRepopsitory;
    private final ProjectRepository projectRepository;

    @Autowired
    public UserServiceImpl(UserRepopsitory userRepopsitory, ProjectRepository projectRepository) {
        this.userRepopsitory = userRepopsitory;
        this.projectRepository = projectRepository;
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
        Project project = new Project("Default");
        user.setProjects(Collections.singletonList(project));
        project.setUser(user);
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

    @Override
    public User getWithProjects(int id) {
        User user = get(id);
        user.setProjects(projectRepository.getAll(id));
        return user;
    }
}
