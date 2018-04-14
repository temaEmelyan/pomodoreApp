package com.temelyan.pomoapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "TASKS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "project_id"},
                name = "task_unique_name_project"),
        indexes = @Index(columnList = "project_id, id", unique = true))
public class Task extends AbstractEntity {
    private String name;

    @JsonBackReference
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    private List<Pomo> pomos;

    @JsonManagedReference
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Pomo> getPomos() {
        return pomos;
    }

    public void setPomos(List<Pomo> pomos) {
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
