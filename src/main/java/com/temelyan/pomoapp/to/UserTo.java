package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Project;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Email
    @NotBlank
    private String email;

    @Size(min = 1, max = 32, message = "length must between 1 and 32 characters")
    private String password;
    private String newPassword;
    private String passwordConfirm;
    private List<Project> projects;

    public UserTo() {
    }

    public UserTo(Integer id, String email, String password) {
        this(id, email, password, null);
    }

    public UserTo(Integer id, String email, String password, List<Project> projects) {
        super(id);
        this.email = email;
        this.password = password;
        this.projects = projects == null ? Collections.emptyList() : projects;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
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
                ", password='" + password + '\'' +
                '}';
    }
}
