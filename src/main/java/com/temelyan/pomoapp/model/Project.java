package com.temelyan.pomoapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "PROJECTS")
public class Project extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "project")
    private List<Pomo> pomo;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
