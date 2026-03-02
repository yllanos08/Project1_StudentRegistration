package registration;

import org.junit.jupiter.api.Test;
import util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TriStateTest {

    @Test
    public void fullTimeStudentTest(){
        //NY student
        // CT student
       //fulltime student that is enrolled > 16 credits
        // fulltime student enrolled < 16 credits
        try{
            Date dob_NY = new Date(2005,07,20);
            Profile profile_NY = new Profile("Ving", "Dang", dob_NY);
            Major major_NY = Major.fromString("CS");
            Student student_NY = new TriState(profile_NY,major_NY, 20,"NY");
            //NY student (4000)
            assertEquals(35649, student_NY.tuition(12));
            assertEquals(40297, student_NY.tuition(20));

            //CT student (5000)
            Date dob_CT = new Date(2005,02,9);
            Profile profile_CT = new Profile("Kevin", "Toan", dob_NY);
            Major major_CT = Major.fromString("CS");
            Student student_CT = new TriState(profile_CT,major_CT, 20,"CT");
            assertEquals(34649, student_CT.tuition(12));
            assertEquals(39297, student_CT.tuition(20));
        }catch(Exception e){
            System.out.println(e);
        }
    }

    @Test
    public void partTimeStudentTest(){
        try{
            Date dob_NY = new Date(2005,07,20);
            Profile profile_NY = new Profile("Ving", "Dang", dob_NY);
            Major major_NY = Major.fromString("CS");
            Student student_NY = new TriState(profile_NY,major_NY, 20,"NY");
            //NY student (4000)
            assertEquals(0, student_NY.tuition(0));
            assertEquals(3107.5, student_NY.tuition(1));
            assertEquals(14727.5, student_NY.tuition(11));

            //CT student (5000)
            Date dob_CT = new Date(2005,02,9);
            Profile profile_CT = new Profile("Kevin", "Toan", dob_NY);
            Major major_CT = Major.fromString("CS");
            Student student_CT = new TriState(profile_CT,major_CT, 20,"CT");
            assertEquals(0, student_CT.tuition(0));
            assertEquals(3107.5, student_CT.tuition(1));
            assertEquals(14727.5, student_CT.tuition(11));
        }catch(Exception e){
            System.out.println(e);
        }
    }
}