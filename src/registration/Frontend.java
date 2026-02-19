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
    final String PRINTSTUDENTS_CMD = "PS";
    final String PRINTSECTIONSBYLOCATION_CMD = "PL";
    final String PRINTSECTIONSBYCODE_CMD = "PC";
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
        boolean active = true;
        //loop forever unless stopped
        while (active) {
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                String[] inputParts = input.split("\\s", 2);
                String inputCmd = inputParts[0];
                String inputParam = (inputParts.length > 1) ? inputParts[1] : "";
                switch(inputCmd){
                    case ADD_CMD -> setADD_CMD(inputParam);
                    case REMOVE_CMD -> setREMOVE_CMD(inputParam);
                    case OFFER_CMD -> setOFFER_CMD(inputParam);
                    case CLOSE_CMD -> setCLOSE_CMD(inputParam);
                    case ENROLL_CMD -> setENROLL_CMD(inputParam);
                    case DROP_CMD -> setDROP_CMD(inputParam);
                    case PRINTSTUDENTS_CMD, PRINTSECTIONSBYLOCATION_CMD, PRINTSECTIONSBYCODE_CMD -> setPRINT_CMD(inputCmd);
                    case STOP_CMD -> active = false;
                    default -> System.out.println(inputCmd + " is an invalid command!");
                }
            }
        }
        System.out.println("Registration System is Terminated.");
    }

    /*
===========================================================
                  Start of Main Functions
===========================================================
 */
    /**
     * Adds student to list of valid students
     * @param input student first name, student last name, MM/DD/YYYY, Major, Completed Credits
     */
    private void setADD_CMD(String input)
    {
        Student addedStudent = new Student();
        String majorString = makeStudent(input, addedStudent);
        if(!isValidMajor(majorString)){
            System.out.println("INVALID: " + majorString + " major does not exixt!");
            return;
        }

        Date dob = addedStudent.getProfile().getDob();

        int creditsCompleted = addedStudent.getCreditsCompleted();

        if(validateDOB(dob) == 0) System.out.println("INVALID: " + dob + " younger than 16 years old.");
        if(validateDOB(dob) == -1) System.out.println("INVALID: " + dob + " cannot be today or a future date.");
        if(validateDOB(dob) == -2) System.out.println("INVALID: " + dob + " is not a valid calendar date!" );
        if(studentList.contains(addedStudent)) System.out.println("[" + addedStudent.getProfile().getFname() + " " + addedStudent.getProfile().getLname() + " "
                + dob + "]" +
                " " + "student is already in the list");
        if(creditsCompleted < 0) System.out.println(creditsCompleted  + " " + "credit is negative!");
        if(validateDOB(dob) == 1 && creditsCompleted > 0 && !studentList.contains(addedStudent)){
            studentList.add(addedStudent);
            System.out.println("[" + addedStudent.getProfile().getFname() + " " + addedStudent.getProfile().getLname() + " "
                    + dob + "]" +
                    " " + "added to the list");
        }

    }

    /**
     * Removes student from valid list of students
     * if student doesnt exist doesnt do anything
     * @param input student first name, student last name, MM/DD/YYYY
     */
    private void setREMOVE_CMD(String input)
    {
        System.out.println("running remove cmd");
        Student removeStudent = findStudent(input);
        StringTokenizer t = new StringTokenizer(input);
        String fname = t.nextToken();
        String lname = t.nextToken();
        String dob = t.nextToken();
        if (removeStudent == null || !studentList.contains(removeStudent)) {
            System.out.println("[" + fname + " " + lname + " "  + dob + "]" + " is not in the student list!");
            return;
        }
        studentList.remove(removeStudent);
    }

    /**
     * Offer a new section of a course if possible
     * @param input course, period, instructor, classroom
     */
    private void setOFFER_CMD(String input) {
        System.out.println("running offer cmd");
        StringTokenizer s = new StringTokenizer(input);
        String courseString = s.nextToken();
        int periodNum = Integer.parseInt(s.nextToken());
        String instructorString = s.nextToken();
        String classroomString = s.nextToken();

        //valid course
        if (!isValidCourse(courseString)) {
            System.out.println("INVALID: " + "course name " + courseString + " does not exist");
            return;
        }
        //valid period
        if (!isValidPeriod(periodNum)){
            System.out.println("INVALID: " + "period " + periodNum + " does not exist");
            return;
        }
        //no time conflicts
        if (!(isValidPeriodTime(courseString, periodNum))){
            System.out.println("INVALID: " + courseString.toUpperCase() + " period " + periodNum + " already exists");
            return;
        }
        //instructor is valid
        if (!isValidInstructor(instructorString)){
            System.out.println("INVALID: " + "instructor " + instructorString + " does not exist");
            return;
        }
        //instructor doesnt have time conflict YSA DID THIS!
        if(!isInstructorTimeConflict(instructorString,periodNum)){
            System.out.println("INVALID: " + instructorString.toUpperCase() + " time conflict.");
            return;
        }
        //classroom is valid
        if (!isValidClassroom(classroomString)) {
            System.out.println("INVALID: " + "classroom " + classroomString + " does not exist");
            return;
        }
        //classroomm is availabile
        if (!isAvailableClassroom(classroomString, periodNum)) {
            Classroom classroom = Classroom.valueOf((classroomString.toUpperCase()));
            System.out.println("INVALID: " + "[" + classroom + ", " + classroom.getBuilding() + ", " + classroom.getCampus() + "]" + " is not available!");
            return;
        }

        //now after all checks we can add the classroom into the schedule
        Course c = Course.valueOf(courseString.toUpperCase());
        String formattedName = instructorString.substring(0, 1).toUpperCase()
                + instructorString.substring(1).toLowerCase();
        Instructor i = Instructor.valueOf(formattedName);
        Time t = getPeriod(periodNum);
        Classroom classroom = Classroom.valueOf(classroomString.toUpperCase());

        Section newSection = new Section(c,i,classroom,t);
        schedule.add(newSection);
        System.out.println("[" + c + " " + t.getStart() + "] "
        + "[" + i + "]" + " "
        + "[" + classroom + ", " + classroom.getBuilding() + ", " + classroom.getCampus() + "]" + " added to the schedule." );
    }

    /**
     * Closes an existing section & remove the section identified by the course number + period
     * @param input course code, period
     */
    private void setCLOSE_CMD(String input)
    {
        StringTokenizer s =  new StringTokenizer(input);
        String courseString = s.nextToken();
        int periodInt = Integer.parseInt(s.nextToken());
        //check if period is valid
        if(periodInt < 0 || periodInt > 6) {
            System.out.println("INVALID: " + "period " + periodInt + " does not exist!");
            return; //exit
        }
        if(!isValidCourse(courseString)){
            System.out.println("INVALID: " + "course name " + courseString + " does not exist!");
            return;
        }

        Course course = Course.valueOf(courseString.toUpperCase());
        Time period = getPeriod(periodInt);
        Section[] sections = schedule.getSections();
        //if we made it here then those two are valid, find course to be removed

        for(int i = 0; i < schedule.getNumSections(); i++ ) //loop through sections
        {
            if(sections[i].getCourse().equals(course) && sections[i].getTime().equals(period) && sections[i].getNumStudents() != 0){
                schedule.remove(sections[i]);
                System.out.println("");
                return;
            }
        }
        //if we loop through all the sections without removing then it doesnt exist
        System.out.println(course + " " +  period.getStart() +  " does not exist");
    }

    /**
     * Enroll student into a section of a couse if possible
     * Checks pre-req of class before placing
     * @param input student first name, student last name, MM/DD/YYYY, course code, section
     */
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
        if(!isValidCourse(courseString)) return;

        Course course = Course.valueOf(courseString.toUpperCase());
        Time period = getPeriod(periodInt);
        Section[] sections = schedule.getSections();
        Section section = null;
        //getting section if it exists
        for(int i = 0; i < sections.length; i++) //loop through sections
        {
            if(sections[i].getCourse().equals(course) && sections[i].getTime().equals(period)){
                section = sections[i];
            }
        }

        if(studentList.contains(student) && section != null) // student list has student and section exists, enroll
        {
            schedule.enroll(section, student);
        }
    }

    /**
     * Drops a course for a student
     * @param input student firstname, student lastname, MM/DD/YYYY, course, section
     */
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
        if(!isValidCourse(courseString)) return;

        Course course = Course.valueOf(courseString.toUpperCase());
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

    /*
===========================================================
                  END of Main Functions
===========================================================
 */



    /*
===========================================================
                  Start of HELPER Functions
===========================================================
 */
    /**
     Print out information based on input (either studentList or schedule ordered by location or course)
     * @param input Specifies what information to print and how it is ordered
     */
    private void setPRINT_CMD(String input)
    {
        switch(input){
            case PRINTSTUDENTS_CMD -> studentList.print();
            case PRINTSECTIONSBYLOCATION_CMD -> schedule.printByClassroom();
            case PRINTSECTIONSBYCODE_CMD -> schedule.printByCourse();
        }
    }



    /**
     * Check if provided DOB is valid
     * @param dob given DOB
     * @return 1 if valid DOB, 0 if younger than 16, -1 if date is in the future, -2 if notvalid
     */
    private int validateDOB(Date dob)
    {
        Calendar calRightNow = Calendar.getInstance();
        int currYear = calRightNow.get(Calendar.YEAR);
        int currMonth = calRightNow.get(Calendar.MONTH);
        int currDay = calRightNow.get(Calendar.DATE);
        Date rightNow = new Date(currYear, currMonth, currDay);

        //future date check
        if(dob.compareTo(rightNow) > 0) return -1;

        //16 y/o check
        if(currYear - dob.getYear() < 16) return 0;
        else if(currYear - dob.getYear() == 16)
        {
            if(currMonth < dob.getMonth()) return 0;
            else if (currMonth == dob.getMonth())
            {
                if(currDay < dob.getDay()) return 0;
            }
        }

        //make sure dob is valid calendar date
        if(!dob.isValid()) return -2;
        return 1;
    }

    /**
     Check if given major is valid
     * @param name major to be checked
     * @return true if major found in enum, false otherwise
     */
    private static boolean isValidMajor(String name)
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
    private static boolean isValidCourse(String name)
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
        for(int i = 0; i < studentList.getSize(); i++){
            Profile check = studentList.getList()[i].getProfile();
            if(check.equals(profile)) student = studentList.getList()[i];
        }
        return student;
    }
    /**
     Fill student attributes using input
     * @param input input provided
     * @param student student to be made/finished
     */
    private static String makeStudent(String input, Student student)
    {
        StringTokenizer s =  new StringTokenizer(input);
        String fname = s.nextToken();
        String lname = s.nextToken();
        String dobString = s.nextToken();
        String majorString = s.nextToken().toUpperCase();
        int creditsCompleted = Integer.parseInt(s.nextToken());
        //check and make major
        Major major;
        if(isValidMajor(majorString))
        {
            major = Major.valueOf(majorString);
        }
        else return majorString;

        StringTokenizer dobToken = new StringTokenizer(dobString);
        int month = Integer.parseInt(dobToken.nextToken("/"));
        int day = Integer.parseInt(dobToken.nextToken("/"));
        int year = Integer.parseInt(dobToken.nextToken("/"));

        Date dob = new Date(year, month, day);

        Profile addedProfile = new Profile (fname, lname, dob);

        student.setProfile(addedProfile);
        student.setMajor(major);
        student.setCreditsCompleted(creditsCompleted);
        return majorString;
    }

    /**
     * Checks if a given course is valid for the period given
     * @param courseString course we want to add
     * @param period period for the course we want to add
     * @return T if time slot is open, F if time slot is closed
     */
    private boolean isValidPeriodTime(String courseString, int period){
        // look through all sections in our schedule
        // if any section in our schedule matches PERIOD + COURSE then return false.
        Course c = Course.valueOf(courseString.toUpperCase());
        Time p = getPeriod(period);
        for(int i = 0; i < schedule.getNumSections(); i++){
            Section section = schedule.getSections()[i];
            if(section.getTime().equals(p) && section.getCourse().equals(c)) return false;
        }
        return true;
    }

    /**
     * Finds if period is a VALID period aka in the enum class
     * @param period int representing period
     * @return T if period is valid, F otherwise
     */
    private boolean isValidPeriod(int period){
        Time time = getPeriod(period);
        for(Time t: Time.values()){
            if(t.equals(time)) return true;
        }
        return false;
    }

    /**
     * Finds if instructor is VALID in instructo enum
     * @param instructor instructor as a string
     * @return T if instructor is valid, F otherwise
     */
    private boolean isValidInstructor(String instructor){
        for(Instructor i: Instructor.values()){
            if(i.toString().equalsIgnoreCase(instructor)) return true;
        }
        return false;
    }

    /**
     * Checks if instructor has time conflict
     * @param instructor instructor being checked
     * @param period period that they want to teachat
     * @return T if available, F otherwise
     */
    private boolean isInstructorTimeConflict(String instructor, int period){
        String formattedName = instructor.substring(0, 1).toUpperCase()
                + instructor.substring(1).toLowerCase();
        Instructor i = Instructor.valueOf(formattedName);
        Time p = getPeriod(period);
        for(int j = 0; j < schedule.getNumSections(); j++){
            Section section = schedule.getSections()[j];
            if(section.getInstructor().equals(i) && section.getTime().equals(p)) return false;
        }
        return true;
    }
    /**
     * Finds if classsroom is VALID in classroom enum
     * @param classroom classroom as string
     * @return T if classroom is valid, F otherwise
     */
    private boolean isValidClassroom(String classroom){
        for(Classroom c: Classroom.values()){
            if(c.toString().equalsIgnoreCase(classroom)) return true;
        }
        return false;
    }

    /**
     * Checks if classroom is availible given period
     * @param classroom classroom as string
     * @param period int period
     * @return T if available, F otherwise
     */
    private boolean isAvailableClassroom(String classroom, int period){
        Time p = getPeriod(period);
        Classroom c = Classroom.valueOf(classroom.toUpperCase());
        //look through schedule
        //look through all the sections
        //if another section uses the classroom at same period then return false

        for(int i = 0; i < schedule.getNumSections(); i++){
            Section section = schedule.getSections()[i];
            if(section.getClassroom().equals(c) && section.getTime().equals(p)) return false;
        }
        return true;
    }

    private boolean isValidStudent(String student){
        Student currStudent;
        return true;
    }

    /*
===========================================================
                  END of Helper Functions
===========================================================
 */





}
