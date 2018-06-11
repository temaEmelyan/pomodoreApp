package com.temelyan.pomoapp.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.temelyan.pomoapp.to.UserTo;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "USERS",
        uniqueConstraints = @UniqueConstraint(columnNames = "email"),
        indexes = {@Index(columnList = "id", unique = true),
                @Index(columnList = "email", unique = true)})
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends AbstractEntity {
    @Email
    @NotBlank
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 8)
    @JsonIgnore
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Project> projects;

    @Column(name = "reset_token")
    @JsonIgnore
    private String resetToken;

    public User() {
    }

    public User(Integer id, @Email @NotBlank String email, @NotBlank @Size(min = 8) String password) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), email);
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                "} " + super.toString();
    }
}
