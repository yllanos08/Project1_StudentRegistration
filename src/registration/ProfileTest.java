package registration;

import org.junit.Test;
import util.Date;
import static org.junit.Assert.*;


public class ProfileTest {

    @Test
    public void testingNegativeLastNameComparison() {
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Tott", new Date(2005,4,8));

        assertEquals(-1, p1.compareTo(p2));
        assertEquals(1, p2.compareTo(p1));
    }

    @Test
    public void testingPositiveLastNameComparison() {
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Tott", new Date(2005,4,8));

        assertEquals(1, p2.compareTo(p1));
    }

    @Test
    public void testingNegativeFirstNameComparison() {
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Bob", "Scott", new Date(2005, 4, 8));

        assertEquals(-1, p1.compareTo(p2));

    }
    @Test
    public void testingPositiveFirstNameComparison() {
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Bob", "Scott", new Date(2005, 4, 8));

        assertEquals(1, p2.compareTo(p1));

    }

    @Test
    public void testingNegativeDOBComparison(){
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Scott", new Date(2005, 4, 9));

        assertEquals(-1, p1.compareTo(p2));
        assertEquals(1, p2.compareTo(p1));
    }

    @Test
    public void testingPositiveDOBComparison(){
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Scott", new Date(2005, 4, 9));

        assertEquals(1, p2.compareTo(p1));
    }

    @Test
    public void testTwoEqualProfiles()
    {
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Scott", new Date(2005,4,8));

        assertEquals(0, p1.compareTo(p2));
    }
}