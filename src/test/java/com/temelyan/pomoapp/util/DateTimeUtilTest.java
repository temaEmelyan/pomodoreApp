package com.temelyan.pomoapp.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DateTimeUtilTest {

    @Test
    public void toHhMmSsTest() {
        assertEquals("00:01:00", DateTimeUtil.toHhMmSs(60));
        assertEquals("01:00:00", DateTimeUtil.toHhMmSs(3600));
        assertEquals("00:00:01", DateTimeUtil.toHhMmSs(1));
        assertEquals("00:03:00", DateTimeUtil.toHhMmSs(180));

        assertNotEquals("00:02:00", DateTimeUtil.toHhMmSs(180));
    }
}