package com.temelyan.pomoapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "POMOS",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"finish", "task_id"},
                name = "pomos_unique_task_datetime"),
        indexes = {@Index(columnList = "finish"),
                @Index(columnList = "task_id, id", unique = true)})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pomo extends AbstractEntity {

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "finish", nullable = false)
    private LocalDateTime finish;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    private Task task;

    public Pomo() {
    }

    public Pomo(LocalDateTime finish, Integer duration) {
        this(null, finish, duration);
    }

    public Pomo(Integer id, LocalDateTime finish, Integer duration) {
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

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pomo)) return false;
        if (!super.equals(o)) return false;
        Pomo pomo = (Pomo) o;
        return Objects.equals(duration, pomo.duration) &&
                Objects.equals(finish, pomo.finish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), duration, finish);
    }

    @Override
    public String toString() {
        return "Pomo{" +
                "duration=" + duration +
                ", finish=" + finish +
                "} " + super.toString();
    }
}
