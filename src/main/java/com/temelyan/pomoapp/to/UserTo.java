package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Project;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    private String email;

    @Size(min = 8, max = 32, message = "length must between 8 and 32 characters")
    private String password;
    private String newPassword;
    private String passwordConfirm;
    private Set<Project> projects;

    public UserTo() {
    }

    public UserTo(Integer id, String email, String password) {
        this(id, email, password, null);
    }

    public UserTo(Integer id, String email, String password, Set<Project> projects) {
        super(id);
        this.email = email;
        this.password = password;
        this.projects = projects == null ? Collections.emptySet() : projects;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
