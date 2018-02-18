package com.temelyan.pomoapp.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateTimeUtilTest {

    @Test
    public void LDTtoString() {
        assertEquals("02 Dec 2017", DateTimeUtil.toString(LocalDate.of(2017, 12, 2)));
        assertEquals("22:22", DateTimeUtil.toString(LocalTime.of(22, 22, 12)));
    }

    @Test
    public void toHhMmSsTest() {
        assertEquals("00:01:00", DateTimeUtil.toHhMmSs(60));
        assertEquals("01:00:00", DateTimeUtil.toHhMmSs(3600));
        assertEquals("00:00:01", DateTimeUtil.toHhMmSs(1));
        assertEquals("00:03:00", DateTimeUtil.toHhMmSs(180));

        assertNotEquals("00:02:00", DateTimeUtil.toHhMmSs(180));
    }
}