package com.temelyan.pomoapp.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "PROJECTS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"name", "user_id"},
                name = "project_unique_name_user"))
public class Project extends AbstractEntity {
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project")
    private List<Pomo> pomo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Project() {
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

    public List<Pomo> getPomo() {
        return pomo == null ? Collections.emptyList() : pomo;
    }

    public void setPomo(List<Pomo> pomo) {
        this.pomo = pomo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
