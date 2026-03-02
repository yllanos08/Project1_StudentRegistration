package registration;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ResidentTest {

    @Test
    public void fullTimeStudent(){
        try{
            Date dob = new Date(2005,07,20);
            Profile profile = new Profile("Ving", "Dang", dob);
            Major major = Major.fromString("CS");
            Student student = new Resident(profile,major,20);
            assertEquals(0,student.tuition(0));
            assertEquals(18824, student.tuition(12));
            assertEquals(20752, student.tuition(20));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void partTimeStudent(){
        try{
            Date dob = new Date(2005,07,20);
            Profile profile = new Profile("Ving", "Dang", dob);
            Major major = Major.fromString("CS");
            Student student = new Resident(profile,major,20);
            assertEquals(0,student.tuition(0));
            assertEquals(2427.5, student.tuition(1));
            assertEquals(7247.5, student.tuition(11));
        }catch(Exception e){
            System.out.println(e);
        }
    }
    @Test
    public void scholarshipStudent(){
        try{
            Date dob = new Date(2005,07,20);
            Profile profile = new Profile("Ving", "Dang", dob);
            Major major = Major.fromString("CS");
            Resident student = new Resident(profile,major,20);
            assertEquals(0,student.tuition(0));
            assertEquals(18824, student.tuition(12));
            assertEquals(20752, student.tuition(20));
            student.setScholarship(5000);
            assertEquals(13824, student.tuition(12));
            assertEquals(15752, student.tuition(20));
            student.setScholarship(10000);
            assertEquals(8824, student.tuition(12));
            assertEquals(10752, student.tuition(20));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}