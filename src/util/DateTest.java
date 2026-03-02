package util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {

    @Test
    public void testDaysInFebNonLeapYear()
    {
        Date d1 = new Date(2025, 29, 2);
        assertFalse(d1.isValid());
    }
    @Test
    public void testDayOutOfRange() {
        Date d1 = new Date (2025, 1, 32);
        assertFalse(d1.isValid());
        Date d2 = new Date(2025, 6, 31);
        assertFalse(d2.isValid());
    }

    @Test
    public void testMonthOutOfRange()
    {
        Date d1 = new Date (2025, 13, 1);
        assertFalse(d1.isValid());
    }

    @Test
    public void testDaysInFebLeapYear()
    {
        Date d1 = new Date(2024, 2, 29);
        assertTrue(d1.isValid());
    }

    @Test
    public void testValidDate()
    {
        Date d1 = new Date(2025, 12, 31);
        assertTrue(d1.isValid());
    }
}