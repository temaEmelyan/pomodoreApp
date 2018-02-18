package com.temelyan.pomoapp.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String toString(LocalTime lt) {
        return lt == null ? "" : lt.format(TIME_FORMATTER);
    }

    public static String toString(LocalDate ld) {
        return ld == null ? "" : ld.format(DATE_FORMATTER);
    }

    public static String toHhMmSs(Integer durationSeconds) {
        LocalTime timeOfDay = LocalTime.ofSecondOfDay(durationSeconds);
        return timeOfDay.format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
