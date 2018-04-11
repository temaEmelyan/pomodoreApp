package com.temelyan.pomoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "PROJECTS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "user_id"},
                name = "project_unique_name_user"))
public class Project extends AbstractEntity {
    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Project() {
    }

    public Project(Integer id, String name, List<Task> tasks, User user) {
        super(id);
        this.name = name;
        this.tasks = tasks;
        this.user = user;
    }

    public Project(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project)) return false;
        if (!super.equals(o)) return false;
        Project project = (Project) o;
        return Objects.equals(name, project.name) &&
                Objects.equals(tasks, project.tasks) &&
                Objects.equals(user, project.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, tasks, user);
    }
}
