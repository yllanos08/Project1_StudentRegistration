package registration;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Calendar;

/**
 User interface class to process command lines
 @author Ysabella Llanos, Kevin Toan
 */

public class Frontend {
    final String ADD_CMD = "A";
    final String REMOVE_CMD = "R";
    final String OFFER_CMD = "O";
    final String CLOSE_CMD = "C";
    final String ENROLL_CMD = "E";
    final String DROP_CMD = "D";
    final String PRINT_CMD = "P";
    final String STOP_CMD = "Q";


    final int PERIOD1 = 1;
    final int PERIOD2 = 2;
    final int PERIOD3 = 3;
    final int PERIOD4 = 4;
    final int PERIOD5 = 5;
    final int PERIOD6 = 6;

    Schedule schedule = new Schedule();
    StudentList studentList = new StudentList();

    // keep under 40 lines
    public void run(){
        String onRunMsg = "Registration System is Running";
        System.out.println(onRunMsg);
        Scanner sc = new Scanner(System.in);
        String input,inputCmd;
        //loop forever unless stopped
        while (true) {
            if (sc.hasNextLine()) {
                input = sc.nextLine();
                inputCmd = input.substring(0, 1); //get first letter
                input = input.substring(1);
                if (inputCmd.equals(ADD_CMD)) {
                    setADD_CMD(input);
                } else if (inputCmd.equals(REMOVE_CMD)) {
                    setREMOVE_CMD(input);
                } else if (inputCmd.equals(OFFER_CMD)) {
                    setOFFER_CMD(input);
                } else if (inputCmd.equals(CLOSE_CMD)) {
                    setCLOSE_CMD(input);
                } else if (inputCmd.equals(ENROLL_CMD)) {
                    setENROLL_CMD(input);
                } else if (inputCmd.equals(DROP_CMD)) {
                    setDROP_CMD(input);
                } else if(inputCmd.equals(PRINT_CMD)){
                    setPRINT_CMD(input.substring(1,2));
                }else if (inputCmd.equals(STOP_CMD)) {
                    break;
                }
                //ERROR
                else{
                    System.out.println(inputCmd + " is an invalid command!");
                }
            }
        }
        System.out.println("Registration System is Terminated.");
    }

    private void setADD_CMD(String input)
    {
        System.out.println("running add cmd");
        Student addedStudent = new Student();
        makeStudent(input, addedStudent);
        Date dob = addedStudent.getProfile().getDob();
        int creditsCompleted = addedStudent.getCreditsCompleted();


        if(isValidDOB(dob) && !studentList.contains(addedStudent) && creditsCompleted > 0) studentList.add(addedStudent);

    }
    private void setREMOVE_CMD(String input)
    {
        System.out.println("running remove cmd");
        Student removeStudent = findStudent(input);
        studentList.remove(removeStudent);
    }

    private void setOFFER_CMD(String input)
    {
        System.out.println("running offer cmd");
    }
    private void setCLOSE_CMD(String input)
    {
        System.out.println("running close cmd");
        StringTokenizer s =  new StringTokenizer(input);
        String courseString = s.nextToken();
        int periodInt = Integer.parseInt(s.nextToken());
        //check if period is valid
        if(periodInt < 0 || periodInt > 6) return; //exit
        if(!containsCourse(courseString)) return;

        Course course = Course.valueOf(courseString);
        Time period = getPeriod(periodInt);

        //if we made it here then those two are valid, find course to be removed

        for(Section section: schedule.getSections()) //loop through sections
        {
            if(section.getCourse().equals(course) && section.getTime().equals(period) && section.getNumStudents() != 0) schedule.remove(section);
        }

    }
    private void setENROLL_CMD(String input){
        System.out.println("running enroll cmd");
        Student student = findStudent(input); //this is student we want to enroll

        StringTokenizer s =  new StringTokenizer(input);
        //skip name and dob
        s.nextToken(); s.nextToken(); s.nextToken();

        //maybe make this into helper later if we want im just writing to finish it (ik its duped from one above)
        String courseString = s.nextToken();
        int periodInt = Integer.parseInt(s.nextToken());
        //check if period is valid
        if(periodInt < 0 || periodInt > 6) return; //exit
        if(!containsCourse(courseString)) return;

        Course course = Course.valueOf(courseString);
        Time period = getPeriod(periodInt);

        Section section = null;
        for(Section sec: schedule.getSections()) //loop through sections
        {
            if(sec.getCourse().equals(course) && sec.getTime().equals(period)) section = sec;
        }

        if(studentList.contains(student) && section != null) // student list has student and section exists, enroll
        {
            schedule.enroll(section, student);
        }


    }

    private void setDROP_CMD(String input){
        System.out.println("running drop cmd");

        Student student = findStudent(input); //this is student we want to drop from sec
        StringTokenizer s =  new StringTokenizer(input);
        //skip name and dob
        s.nextToken(); s.nextToken(); s.nextToken();

        //maybe make this into helper later if we want im just writing to finish it (ik its duped from one above)
        String courseString = s.nextToken();
        int periodInt = Integer.parseInt(s.nextToken());
        //check if period is valid
        if(periodInt < 0 || periodInt > 6) return; //exit
        if(!containsCourse(courseString)) return;

        Course course = Course.valueOf(courseString);
        Time period = getPeriod(periodInt);

        Section section = null;
        for(Section sec: schedule.getSections()) //loop through sections
        {
            if(sec.getCourse().equals(course) && sec.getTime().equals(period)) section = sec;
        }

        if(studentList.contains(student) && section != null) // student list has student and section exists, enroll
        {
            schedule.drop(section, student);
        }

    }

    private void setPRINT_CMD(String input)
    {
        if(input.equals("S")) PS_CMD();
        if(input.equals("L")) PL_CMD();
        if(input.equals("C")) PC_CMD();
    }
    /**
     Print list of students ordered by last name, first name, then DOB
     */
    private void PS_CMD()
    {
        studentList.print();
    }

    /**
     Print list of sections ordered by campus then building
     */
    private void PL_CMD()
    {
        schedule.printByClassroom();
    }

    /**
    Print list of sections ordered by course number then period
     */
    private void PC_CMD()
    {
        schedule.printByCourse();
    }

    /**
     Check if provided DOB is valid
     * @param dob given DOB
     * @return true if DOB is valid, false otherwise
     */
    private boolean isValidDOB (Date dob)
    {
        Calendar calRightNow = Calendar.getInstance();
        int currYear = calRightNow.get(Calendar.YEAR);
        int currMonth = calRightNow.get(Calendar.MONTH);
        int currDay = calRightNow.get(Calendar.DATE);
        Date rightNow = new Date(currYear, currMonth, currDay);

        //16 y/o check
        if(currYear - dob.getYear() < 16) return false;
        else if(currYear - dob.getYear() == 16)
        {
            if(currMonth < dob.getMonth()) return false;
            else if (currMonth == dob.getMonth())
            {
                if(currDay < dob.getDay()) return false;
            }
        }
        //make sure dob is valid calendar date
        return (dob.isValid() && (rightNow.compareTo(dob) > 0));

    }

    /**
     Check if given major is valid
     * @param name major to be checked
     * @return true if major found in enum, false otherwise
     */
    private static boolean containsMajor(String name)
    {
        for(Major major: Major.values())
        {
            if(major.name().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     Get Time using int
     * @param p integer version of period
     * @return period as a Time enum
     */
    private Time getPeriod(int p){
        Time period = null;

        return switch (p) {
            case PERIOD1 -> Time.PERIOD1;
            case PERIOD2 -> Time.PERIOD2;
            case PERIOD3 -> Time.PERIOD3;
            case PERIOD4 -> Time.PERIOD4;
            case PERIOD5 -> Time.PERIOD5;
            case PERIOD6 -> Time.PERIOD6;
            default -> period;
        };

    }

    /**
     Check if given course is valid
     * @param name course to be checked
     * @return true if the course is found in enum, false otherwise
     */
    private static boolean containsCourse(String name)
    {
        for(Course course: Course.values())
        {
            if(course.name().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     Find student using their name and DOB
     * @param input Student's first name, last name, and DOB in one string
     * @return student that matches input, null otherwise
     */
    private Student findStudent(String input)
    {
        Student student = new Student();
        StringTokenizer str =  new StringTokenizer(input);
        String fname = str.nextToken();
        String lname = str.nextToken();
        String dobString = str.nextToken();

        StringTokenizer dobToken = new StringTokenizer(dobString);
        int month = Integer.parseInt(dobToken.nextToken("/"));
        int day = Integer.parseInt(dobToken.nextToken("/"));
        int year = Integer.parseInt(dobToken.nextToken("/"));

        Date dob = new Date(year, month, day);

        Profile profile = new Profile (fname, lname, dob);
        for(Student s : studentList.getList())
        {
            if(s.getProfile().equals(profile)) student = s;
        }

        return student;
    }
    /**
     Fill student attributes using input
     * @param input input provided
     * @param student student to be made/finished
     */
    private static void makeStudent(String input, Student student)
    {
        StringTokenizer s =  new StringTokenizer(input);
        String fname = s.nextToken();
        String lname = s.nextToken();
        String dobString = s.nextToken();
        String majorString = s.nextToken();
        int creditsCompleted = Integer.parseInt(s.nextToken());
        //check and make major
        Major major;
        if(containsMajor(majorString))
        {
            major = Major.valueOf(majorString);
        }
        else return;

        StringTokenizer dobToken = new StringTokenizer(dobString);
        int month = Integer.parseInt(dobToken.nextToken("/"));
        int day = Integer.parseInt(dobToken.nextToken("/"));
        int year = Integer.parseInt(dobToken.nextToken("/"));

        Date dob = new Date(year, month, day);

        Profile addedProfile = new Profile (fname, lname, dob);

        student.setProfile(addedProfile);
        student.setMajor(major);
        student.setCreditsCompleted(creditsCompleted);
    }





}
