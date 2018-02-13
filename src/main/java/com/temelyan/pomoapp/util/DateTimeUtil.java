package com.temelyan.pomoapp.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static String toHhMmSs(Integer durationSeconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(durationSeconds);
        return timeOfDay.toString();
    }
}
