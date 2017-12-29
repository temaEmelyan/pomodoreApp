package com.temelyan.pomoapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "POMOS")
public class Pomo extends AbstractEntity {

    @Column(name = "start")
    private
    LocalDateTime start;

    @Column(name = "finish")
    private
    LocalDateTime finish;

    @ManyToOne
    private
    User user;

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getFinish() {
        return finish;
    }

    public void setFinish(LocalDateTime finish) {
        this.finish = finish;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
