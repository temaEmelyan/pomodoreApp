package com.temelyan.pomoapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "POMOS", uniqueConstraints = [UniqueConstraint(columnNames = ["finish", "task_id"], name = "pomos_unique_task_datetime")], indexes = [Index(columnList = "finish"), Index(columnList = "task_id, id", unique = true)])
@JsonIgnoreProperties(ignoreUnknown = true)
open class Pomo : AbstractEntity {

    @Column(name = "duration", nullable = false)
    var duration: Int? = null

    @Column(name = "finish", nullable = false)
    var finish: LocalDateTime? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @NotNull
    var task: Task? = null

    constructor()

    constructor(finish: LocalDateTime, duration: Int?) : this(null, finish, duration)

    constructor(id: Int?, finish: LocalDateTime, duration: Int?) : super(id) {
        this.duration = duration
        this.finish = finish
    }

}
