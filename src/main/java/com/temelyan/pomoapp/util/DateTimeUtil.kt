package com.temelyan.pomoapp.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {
    private val DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy")
    private val TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm")

    fun toString(lt: LocalTime?): String {
        return if (lt == null) "" else lt.format(TIME_FORMATTER)
    }

    fun toString(ld: LocalDate?): String {
        return if (ld == null) "" else ld.format(DATE_FORMATTER)
    }

    fun toHhMmSs(durationSeconds: Int?): String {
        val timeOfDay = LocalTime.ofSecondOfDay(durationSeconds!!.toLong())
        return timeOfDay.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }
}
