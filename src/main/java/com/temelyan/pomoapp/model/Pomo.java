package com.temelyan.pomoapp.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.SECONDS;

@Entity
@Table(name = "POMOS", uniqueConstraints = {@UniqueConstraint(columnNames = {"finish", "user_id"}, name = "pomos_unique_user_datetime_idx")})
public class Pomo extends AbstractEntity {

    @Column(name = "duration", nullable = false)
    private
    Integer duration;

    @Column(name = "finish", nullable = false)
    private
    LocalDateTime finish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

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

    public String getDurationString() {
        return Duration.of(duration, SECONDS).toString();
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public String getFinishString() {
        return finish.toString().replace("T", " ");
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
