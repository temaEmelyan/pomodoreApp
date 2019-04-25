package com.temelyan.pomoapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import com.temelyan.pomoapp.JsonViews.TaskViews
import javax.persistence.*

@Entity
@Table(name = "TASKS", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "project_id"], name = "task_unique_name_project")], indexes = [Index(columnList = "project_id, id", unique = true)])
@JsonIgnoreProperties(ignoreUnknown = true)
open class Task : AbstractEntity {
    @JsonView(TaskViews.Default::class)
    var name: String? = null

    @JsonView(TaskViews.IncludePomos::class)
    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    var pomos: MutableSet<Pomo>? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    var project: Project? = null

    constructor()

    constructor(name: String) {
        this.name = name
    }

    constructor(name: String, project: Project) : this(name) {
        this.project = project
    }

    @JsonView(TaskViews.Default::class)
    override fun getId(): Int? {
        return super.getId()
    }
}
