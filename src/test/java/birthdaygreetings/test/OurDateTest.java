package birthdaygreetings.test;

import static birthdaygreetings.test.DateHelper.date;
import static org.junit.Assert.*;

import birthdaygreetings.OurDate;

import org.junit.Test;

public class OurDateTest {
    @Test
    public void getters() throws Exception {
        OurDate ourDate = date("1789/01/24");
        assertEquals(1, ourDate.getMonth());
        assertEquals(24, ourDate.getDay());
    }

    @Test
    public void isSameDate() throws Exception {
        OurDate ourDate = date("1789/01/24");
        OurDate sameDay = date("2001/01/24");
        OurDate notSameDay = date("1789/01/25");
        OurDate notSameMonth = date("1789/02/25");

        assertTrue("same", ourDate.isSameDay(sameDay));
        assertFalse("not same day", ourDate.isSameDay(notSameDay));
        assertFalse("not same month", ourDate.isSameDay(notSameMonth));
    }

    @Test
    public void equality() throws Exception {
        OurDate base = date("2000/01/02");
        OurDate same = date("2000/01/02");
        OurDate different = date("2000/01/04");

        assertFalse(base.equals(null));
        assertFalse(base.equals(""));
        assertTrue(base.equals(base));
        assertTrue(base.equals(same));
        assertFalse(base.equals(different));
    }
}
