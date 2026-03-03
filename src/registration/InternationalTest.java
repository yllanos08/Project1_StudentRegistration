package registration;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.*;

class InternationalTest {

    @Test
    public void isStudyAbroadTest(){
        try{
            Date dob = new Date(2005,07,20);
            Profile profile = new Profile("Ving", "Dang", dob);
            Major major = Major.fromString("CS");
            Student studentAbroad = new International(profile,major, 20,true);

            assertEquals(0,studentAbroad.tuition(0));
            assertEquals(7041,studentAbroad.tuition(2));
            assertEquals(7041,studentAbroad.tuition(12));
            assertEquals(8203,studentAbroad.tuition(17));
            assertEquals(11689,studentAbroad.tuition(20));
        }catch (Exception e){
            System.out.println(e);
        }

    }

    @Test
    public void isNotStudyAbroadTest(){
        try{
            Date dob = new Date(2005,07,20);
            Profile profile = new Profile("Ving", "Dang", dob);
            Major major = Major.fromString("CS");
            Student studentNotAbroad = new International(profile,major, 20,false);

            assertEquals(0,studentNotAbroad.tuition(0));
            assertEquals(4269.5,studentNotAbroad.tuition(2));
            assertEquals(42799,studentNotAbroad.tuition(12));
            assertEquals(43961,studentNotAbroad.tuition(17));
            assertEquals(47447,studentNotAbroad.tuition(20));
        }catch (Exception e){
            System.out.println(e);
        }
    }
}