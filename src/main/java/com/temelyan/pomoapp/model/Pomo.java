package com.temelyan.pomoapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "POMOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"finish", "user_id"}, name = "pomos_unique_user_datetime_idx")})
public class Pomo extends AbstractEntity {

    @Column(name = "duration", nullable = false)
    private
    Integer duration;

    @Column(name = "finish", nullable = false)
    @Type(type = "com.temelyan.pomoapp.util.LocalDateTimeUserType")
    private LocalDateTime finish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Project project;

    public Pomo() {
    }

    public Pomo(LocalDateTime finish, Integer duration) {
        this(null, finish, duration);
    }

    public Pomo(Integer id, LocalDateTime finish, int duration) {
        super(id);
        this.duration = duration;
        this.finish = finish;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer start) {
        this.duration = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
