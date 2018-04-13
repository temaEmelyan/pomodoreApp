package com.temelyan.pomoapp.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.temelyan.pomoapp.to.UserTo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USERS",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"),
        indexes = {@Index(columnList = "id", unique = true),
                @Index(columnList = "email", unique = true)})
public class User extends AbstractEntity {
    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 1)
    @JsonIgnore
    private String password;

    @JsonBackReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Project> projects;

    @Column(name = "reset_token")
    @JsonIgnore
    private String resetToken;

    public User() {
    }

    public User(Integer id, @Email @NotBlank String email, @NotBlank @Size(min = 1) String password) {
        super(id);
        this.email = email;
        this.password = password;
    }

    public User(UserTo userTo) {
        this(userTo.getId(), userTo.getEmail(), userTo.getPassword());
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(resetToken, user.resetToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email, password, resetToken);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                "} " + super.toString();
    }
}
