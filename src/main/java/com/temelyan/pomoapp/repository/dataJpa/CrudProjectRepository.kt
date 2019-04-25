package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Project
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

import java.time.LocalDateTime

interface CrudProjectRepository : JpaRepository<Project, Int> {
    @Query("select p from Project p " +
            "left join fetch p.tasks t " +
            "where p.user.id=:user_id")
    fun findAllByUserIdFetchWithTasks(@Param("user_id") userId: Int): Set<Project>

    @Query("select pr from Project pr " +
            "join fetch pr.tasks t " +
            "join fetch t.pomos p " +
            "where pr.user.id=:user_id and (p.finish between :from_date and :to_date)")
    fun findAllByUserIdFetchWithTasksAndPomosInDateRange(
            @Param("user_id") userId: Int,
            @Param("from_date") from: LocalDateTime,
            @Param("to_date") to: LocalDateTime
    ): Set<Project>
}
