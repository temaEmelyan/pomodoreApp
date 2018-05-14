package com.temelyan.pomoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.temelyan.pomoapp.JsonViews.TaskViews;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TASKS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "project_id"},
                name = "task_unique_name_project"),
        indexes = @Index(columnList = "project_id, id", unique = true))
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task extends AbstractEntity {
    @JsonView(TaskViews.Default.class)
    private String name;

    @JsonView(TaskViews.IncludePomos.class)
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private Set<Pomo> pomos;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public Task() {
    }

    public Task(String name) {
        this.name = name;
    }

    public Task(String name, Project project) {
        this.name = name;
        this.project = project;
    }

    @JsonView(TaskViews.Default.class)
    @Override
    public Integer getId() {
        return super.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pomo> getPomos() {
        return pomos;
    }

    public void setPomos(Set<Pomo> pomos) {
        this.pomos = pomos;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                "} " + super.toString();
    }
}
