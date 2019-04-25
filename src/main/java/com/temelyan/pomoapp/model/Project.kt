package com.temelyan.pomoapp.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonView
import com.temelyan.pomoapp.JsonViews.ProjectViews
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity
@Table(name = "PROJECTS", uniqueConstraints = [UniqueConstraint(columnNames = ["name", "user_id"], name = "project_unique_name_user")], indexes = [Index(columnList = "user_id, id", unique = true)])
@JsonIgnoreProperties(ignoreUnknown = true)
open class Project : AbstractEntity {
    @JsonView(ProjectViews.Default::class)
    @NotBlank
    @NotEmpty
    @NotNull
    @Column(name = "name", nullable = false)
    var name: String? = null

    @JsonView(ProjectViews.IncludeTasks::class)
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
    var tasks: MutableSet<Task>? = null

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var user: User? = null

    constructor()

    constructor(name: String) {
        this.name = name
    }

    constructor(id: Int?, name: String, tasks: MutableSet<Task>, user: User) : super(id) {
        this.name = name
        this.tasks = tasks
        this.user = user
    }

    @JsonView(ProjectViews.Default::class)
    override fun getId(): Int? {
        return super.getId()
    }
}
