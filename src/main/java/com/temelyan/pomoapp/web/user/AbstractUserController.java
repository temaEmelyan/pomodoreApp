package com.temelyan.pomoapp.web.user;

import com.temelyan.pomoapp.AuthorizedUser;
import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.UserService;
import com.temelyan.pomoapp.to.UserTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;

import static com.temelyan.pomoapp.Util.ValidationUtil.assureIdConsistent;
import static com.temelyan.pomoapp.Util.ValidationUtil.checkNew;

public class AbstractUserController {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserService userService;

    protected void create(User user) {
        logger.info("create {}", user);
        checkNew(user);
        userService.create(user);
    }

    protected void update(@Valid UserTo userTo, int id) {
        logger.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, AuthorizedUser.id());
        userService.update(userTo);
    }
}
