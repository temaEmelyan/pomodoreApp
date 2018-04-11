package com.temelyan.pomoapp.validator;

import com.temelyan.pomoapp.model.User;
import com.temelyan.pomoapp.service.UserService;
import com.temelyan.pomoapp.to.UserTo;
import com.temelyan.pomoapp.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class UserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UserTo.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserTo userTo = (UserTo) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordConfirm", "NotEmpty");

        Optional<User> user = Optional.empty();
        try {
            user = Optional.of(userService.loadByEmail(userTo.getEmail()));
        } catch (NotFoundException ignored) {
        }

        if (user.isPresent()) {
            errors.rejectValue("email", "Duplicate.userForm.email");
        }
        if (userTo.getPassword().length() < 1 || userTo.getPassword().length() > 256) {
            errors.rejectValue("password", "Size.userForm.password");
        }
        if (!userTo.getPasswordConfirm().equals(userTo.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}