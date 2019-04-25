package com.temelyan.pomoapp.repository.dataJpa

import com.temelyan.pomoapp.model.Pomo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.transaction.annotation.Transactional

import java.time.LocalDateTime

@Transactional(readOnly = true)
interface CrudPomoRepository : JpaRepository<Pomo, Int> {

    @Query("select p from Pomo p " +
            "join fetch p.task t " +
            "join fetch t.project pr " +
            "join fetch pr.user u " +
            "where u.id=:user_id " +
            "and (p.finish between :from_date and :to_date)" +
            "order by p.task.project.name, p.task.name, p.finish DESC")
    fun getAllForUserInDateRange(@Param("from_date") fromDate: LocalDateTime,
                                 @Param("to_date") toDate: LocalDateTime,
                                 @Param("user_id") userId: Int): List<Pomo>
}
