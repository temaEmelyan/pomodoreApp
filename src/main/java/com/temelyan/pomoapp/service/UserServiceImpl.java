package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.model.Task;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.repository.ProjectRepository;
import com.temelyan.pomoapp.repository.TaskRepository;
import com.temelyan.pomoapp.repository.UserRepopsitory;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.temelyan.pomoapp.util.UserUtil.prepareToSave;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepopsitory userRepopsitory;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public UserServiceImpl(UserRepopsitory userRepopsitory,
                           ProjectRepository projectRepository,
                           TaskRepository taskRepository) {
        this.userRepopsitory = userRepopsitory;
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
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

    @Override
    public User update(User user) {
        return userRepopsitory.save(user);
    }

    @Transactional
    @Override
    public User update(UserTo userTo) {
        User user = get(userTo.getId());
        return userRepopsitory.save(prepareToSave(UserUtil.updateFromTo(user, userTo)));
    }

    @Override
    public User findUserByResetToken(String token) {
        return userRepopsitory.findUserByResetToken(token);
    }

    @Override
    public User create(User user) {
        userRepopsitory.save(prepareToSave(user));
        Project project = new Project("Work");
        projectRepository.save(project, user.getId());
        taskRepository.save(new Task("Do work", project));
        logger.info("New user {} created", user);
        return user;
    }

    private User get(int id) {
        return userRepopsitory.get(id);
    }
}
