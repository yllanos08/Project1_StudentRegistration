package registration;
import util.Date;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Calendar;
import java.text.DecimalFormat;
/**
 User interface class to process command lines
 @author Ysabella Llanos, Kevin Toan
 */

public class Frontend {
    final String ADD_RESIDENT_CMD = "AR";
    final String ADD_NONRESIDENT_CMD = "AN";
    final String ADD_TRISTATE_CMD = "AT";
    final String ADD_INTERNATIONAL_CMD = "AI";
    final String LOAD_CMD = "L";
    final String SCHOLARSHIP_CMD = "S";
    final String REMOVE_CMD = "R";
    final String OFFER_CMD = "O";
    final String CLOSE_CMD = "C";
    final String ENROLL_CMD = "E";
    final String DROP_CMD = "D";
    final String PRINTSTUDENTS_CMD = "PS";
    final String PRINTSECTIONSBYLOCATION_CMD = "PL";
    final String PRINTSECTIONSBYCODE_CMD = "PC";
    final String PRINTBYTUITION_CMD = "PT";
    final String PRINTBYGRAD_CMD = "PG";
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
                    case ADD_RESIDENT_CMD, ADD_NONRESIDENT_CMD, ADD_TRISTATE_CMD, ADD_INTERNATIONAL_CMD -> setADD_CMD(input);
                    case LOAD_CMD -> setLOAD_CMD(inputParam);
                    case SCHOLARSHIP_CMD -> setSCHOLARSHIP_CMD(inputParam);
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
     * @param input specific Add cmd, student first name, student last name, MM/DD/YYYY, Major, Completed Credits
     *              (OPTIONAL) State, (OPTIONAL) isStudyAbroad
     */
    private void setADD_CMD(String input)
    {
        try{
            StringTokenizer tokenizer = new StringTokenizer(input);
            String cmd = tokenizer.nextToken();
            int remainingTokens = tokenizer.countTokens();
            // 1. Validate Parameter Count FIRST
            if (cmd.equals(ADD_RESIDENT_CMD) || cmd.equals(ADD_NONRESIDENT_CMD)) {
                if (remainingTokens < 5) throw new Exception("Missing data tokens.(AR & AN)");
            } else if (cmd.equals(ADD_TRISTATE_CMD) || cmd.equals(ADD_INTERNATIONAL_CMD)) {
                if (remainingTokens < 6) throw new Exception("Missing data tokens.(AT & AI)");
            }
            Profile profile = profileParser(tokenizer);
            String majorString = tokenizer.nextToken();
            Major major = Major.fromString(majorString);

            String creditString = tokenizer.nextToken();
            int creditsCompleted;
            try{
                creditsCompleted = Integer.parseInt(creditString);
            }catch(NumberFormatException exception){
                throw new Exception("INVALID: " + creditString + " is not a valid integer!");
            }
            //create student based on input
            Student newStudent;
            switch(cmd){
                case ADD_RESIDENT_CMD -> newStudent = new Resident(profile, major, creditsCompleted);
                case ADD_NONRESIDENT_CMD -> newStudent = new NonResident(profile, major, creditsCompleted);
                case ADD_TRISTATE_CMD -> {
                    String state = tokenizer.nextToken();
                    newStudent = new TriState(profile, major, creditsCompleted,state);
                }
                case ADD_INTERNATIONAL_CMD -> {
                    boolean isStudyAbroad = Boolean.parseBoolean(tokenizer.nextToken());
                    newStudent = new International(profile, major, creditsCompleted,isStudyAbroad);
                }
                //this default should never be hit anyways
                default -> throw new Exception("INVALID COMMAND");
            }
            //checks
            if(studentList.contains(newStudent)) throw new Exception(newStudent.getProfile().toString() +
                    " " + "student is already in the list");
            if(creditsCompleted < 0) throw new Exception("INVALID: " + creditsCompleted + " credit is negative!");

            studentList.add(newStudent);
            System.out.println(newStudent.getProfile().toString() + " " + newStudent.getType() + " added to the list");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Loads a .txt file and executes all commands
     * @param input txt file list of students to be added
     */
    private void setLOAD_CMD(String input){
        File file = new File("students.txt");
        try{
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()){
                String rawLine = reader.nextLine().trim();
                String fullCmd = parseStudentsTxt(rawLine);
                setADD_CMD(fullCmd);
            }
        }catch(FileNotFoundException exception){
            System.out.println("Invalid File.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("Student list loaded from text file");
    }


    /**
     * sets scholarship for a student
     * @param input
     */
    private void setSCHOLARSHIP_CMD(String input){
        try
        {
            Student student = findStudent(input);
            StringTokenizer tokenizer = new StringTokenizer(input);
            String scholarshipString = "";
            DecimalFormat df = new DecimalFormat("$#,##0");
            int scholarship;
            //get last token
            while (tokenizer.hasMoreTokens()) {
                scholarshipString = tokenizer.nextToken();
            }
            if(student instanceof Resident)
            {
                try{
                    scholarship = Integer.parseInt(scholarshipString);
                } catch(NumberFormatException exception){
                    throw new Exception("INVALID: amount is not an integer.");
                }

                if(scholarship < 1 || scholarship > 10000) throw new Exception ("INVALID: scholarship amount cannot be 0 or negative or greater than $10,000");
                if(getCurrCredits(student) < 12) throw new Exception(student.getProfile().toString() + " enrolled in less than 12 credits, not eligible for the scholarship.");

                ((Resident) student).setScholarship(scholarship);
                String formattedScholarship = df.format(scholarship);
                System.out.println("Scholarship " + formattedScholarship + " updated for " + student.getProfile().toString());
            }
            else {
                throw new Exception(student.getProfile().toString() + " is a non-resident not eligible for the scholarship.");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());

        }

    }
    /**
     * Removes student from valid list of students
     * if student doesnt exist doesnt do anything
     * @param input student first name, student last name, MM/DD/YYYY
     */
    private void setREMOVE_CMD(String input)
    {
        try{
            Student removeStudent = findStudent(input);
            studentList.remove(removeStudent);
            System.out.println(removeStudent.getProfile().toString() + " removed from the list.");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Offer a new section of a course if possible
     * @param input string as course, period, instructor, classroom
     */
    private void setOFFER_CMD(String input) {
        try{
            StringTokenizer s = new StringTokenizer(input);
            String courseString = s.nextToken();
            int periodNum = Integer.parseInt(s.nextToken());
            String instructorString = s.nextToken();
            String classroomString = s.nextToken();

            //valid course
            if (!isValidCourse(courseString))throw new Exception("INVALID: " + "course name " + courseString + " does not exist");

            //valid period
            if (!isValidPeriod(periodNum))throw new Exception("INVALID: " + "period " + periodNum + " does not exist");
            //no time conflicts
            if (!(isValidPeriodTime(courseString, periodNum)))throw new Exception("INVALID: " + courseString.toUpperCase() + " period " + periodNum + " already exists");
            //instructor is valid
            if (!isValidInstructor(instructorString))throw new Exception("INVALID: " + "instructor " + instructorString + " does not exist");
            //instructor doesnt have time conflict YSA DID THIS!
            if(!isInstructorTimeConflict(instructorString,periodNum))throw new Exception("INVALID: " + instructorString.toUpperCase() + " time conflict.");

            //classroom is valid
            if (!isValidClassroom(classroomString))throw new Exception("INVALID: " + "classroom " + classroomString + " does not exist");
            //classroomm is availabile
            if (!isAvailableClassroom(classroomString, periodNum)) {
                Classroom classroom = Classroom.valueOf((classroomString.toUpperCase()));
                throw new Exception("INVALID: " + "[" + classroom + ", " + classroom.getBuilding() + ", " + classroom.getCampus() + "]" + " is not available!");
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
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Closes an existing section & remove the section identified by the course number + period
     * @param input course code, period
     */
    private void setCLOSE_CMD(String input) {
        try {
        StringTokenizer s = new StringTokenizer(input);
        String courseString = s.nextToken();
        int periodInt = Integer.parseInt(s.nextToken());
        //check if period is valid
        if (!isValidPeriod(periodInt)){
            throw new Exception("INVALID: " + "period " + periodInt + " does not exist!");
        }
        if (!isValidCourse(courseString)) {
            throw new Exception("INVALID: course name " + courseString + " does not exist.");
        }

        Section section = findSection(courseString, periodInt, schedule);
        schedule.remove(section);
        Time period = getPeriod(periodInt);
        System.out.println(courseString.toUpperCase() + " " + period.getStart() + " removed.");
    } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Enroll student into a section of a couse if possible
     * Checks pre-req of class before placing
     * @param input student first name, student last name, MM/DD/YYYY, course code, section/period
     */
    private void setENROLL_CMD(String input){
        try{
            Student student = findStudent(input); //this is student we want to drop from sec
            StringTokenizer s =  new StringTokenizer(input);
            //skip name and dob
            s.nextToken(); s.nextToken(); s.nextToken();
            //maybe make this into helper later if we want im just writing to finish it (ik its duped from one above)
            String courseString = s.nextToken();
            int periodInt = Integer.parseInt(s.nextToken());
            if(!isValidPeriod(periodInt)){
                throw new Exception("INVALID: period " + periodInt + " does not exist.");
            }
            if(!isValidCourse(courseString)){
                throw new Exception("INVALID: course " + courseString + " does not exist.");
            }
            // this will throw an exception if the section isnt found
            Section section = findSection(courseString, periodInt, schedule);
            //enroll will throw an exception if it doesnt work
            schedule.enroll(section, student);
            System.out.println("[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() + "]" +
                    " added to " + section.getCourse() + " " + section.getPeriod().getStart() + ".");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    /**
     * Drops a course for a student
     * @param input student firstname, student lastname, MM/DD/YYYY, course, section
     */
    private void setDROP_CMD(String input){
        //find student returns an exception
        try{
            Student student = findStudent(input); //this is student we want to drop from sec
            StringTokenizer s =  new StringTokenizer(input);
            //skip name and dob
            s.nextToken(); s.nextToken(); s.nextToken();
            //maybe make this into helper later if we want im just writing to finish it (ik its duped from one above)
            String courseString = s.nextToken();
            int periodInt = Integer.parseInt(s.nextToken());
            if(!isValidPeriod(periodInt)){
                throw new Exception("INVALID: period " + periodInt + " does not exist.");
            }
            if(!isValidCourse(courseString)){
                throw new Exception("INVALID: course " + courseString + " does not exist.");
            }

            Section section = findSection(courseString, periodInt, schedule);
            //enroll will throw an exception if it doesnt work
            schedule.drop(section, student);
            System.out.println("[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() + "]" +
                    " dropped from " + section.getCourse() + " " + section.getPeriod().getStart() + ".");

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

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
     * Parses a line of data from students.txt
     * @param rawLine raw line of data
     * @return fullCmd in format "A[X] fname lname DOB (extra parameters)"
     * Where X is specific command based on .txt
     * @throws Exception if line cmds are invalid
     */
    private static String parseStudentsTxt(String rawLine) throws Exception {
        StringTokenizer line = new StringTokenizer(rawLine);
        String type = line.nextToken();
        String data = rawLine.substring(type.length()).trim();
        String fullCmd = "";
        switch(type){
            case "R" -> fullCmd = "AR " + data;
            case "I" -> fullCmd = "AI " + data;
            case "N" -> fullCmd = "AN " + data;
            case "T" -> fullCmd = "AT " + data;
            default -> throw new Exception("Invalid line");
        }
        return fullCmd;
    }
    /**
     * Parses through input and creates a new Profile object
     * @param tokenizer input to be tokenized
     * @throws Exception if parameters are incorrect
     * @return new profile
     */
    private Profile profileParser(StringTokenizer tokenizer) throws Exception{

        String fname = tokenizer.nextToken();
        String lname = tokenizer.nextToken();

        StringTokenizer dobToken = new StringTokenizer(tokenizer.nextToken());
        int month,day,year;
        try{
            month = Integer.parseInt(dobToken.nextToken("/"));
            day = Integer.parseInt(dobToken.nextToken("/"));
            year = Integer.parseInt(dobToken.nextToken("/"));
        }catch(NumberFormatException exception){
            throw new Exception("INVALID: " + dobToken + " is not an integer!");
        }

        Date dob = new Date(year,month,day);
        validateDOB(dob);
        return new Profile(fname, lname, dob);
    }
    /**
     * Throws exception if DOB is valid
      * @param dob date of birth to be checked
     * @throws Exception different exception depending on error: future check, 16 y/o check, valid calendar date
     */
    private void validateDOB(Date dob) throws Exception
    {

        Calendar calRightNow = Calendar.getInstance();
        int currYear = calRightNow.get(Calendar.YEAR);
        int currMonth = calRightNow.get(Calendar.MONTH);
        int currDay = calRightNow.get(Calendar.DATE);
        Date rightNow = new Date(currYear, currMonth, currDay);

        //future date check
        if(dob.compareTo(rightNow) > 0) throw new Exception("INVALID: " + dob + " cannot be today or a future date.");

        //16 y/o check
        if(currYear - dob.getYear() < 16) throw new Exception("INVALID: " + dob + " younger than 16 years old.");
        else if(currYear - dob.getYear() == 16)
        {
            if(currMonth < dob.getMonth()) throw new Exception("INVALID: " + dob + " younger than 16 years old.");
            else if (currMonth == dob.getMonth())
            {
                if(currDay < dob.getDay()) throw new Exception("INVALID: " + dob + " younger than 16 years old.");
            }
        }
        //make sure dob is valid calendar date
        if(!dob.isValid()) throw new Exception("INVALID: " + dob + " is not a valid calendar date!");
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
     * Finds student in student roster based on string
     * @param input string input, fname lname dob format
     * @return Student that matches input
     * @throws Exception - if student cannot be found
     */
    private Student findStudent (String input) throws Exception
    {
        Student student;
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
        for(int i = 0; i < studentList.size(); i++){
            Profile check = studentList.get(i).getProfile();
            if(check.equals(profile)) {
                student = studentList.get(i);
                return student;
            }
        }
        throw new Exception("INVALID: [" + fname + " " + lname + " " + dobString +  "]" + " does not exist.");
    }
//    /**
//     Fill student attributes using input
//     * @param input input provided
//     * @param student student to be made/finished
//     */
//    private static String makeStudent(String input, Student student) throws Exception
//    {
//        StringTokenizer s =  new StringTokenizer(input);
//        String fname = s.nextToken();
//        String lname = s.nextToken();
//        String dobString = s.nextToken();
//        String majorString = s.nextToken().toUpperCase();
//        int creditsCompleted = Integer.parseInt(s.nextToken());
//        //check and make major
//        Major major;
//        if(isValidMajor(majorString))
//        {
//            major = Major.fromString(majorString);
//        }
//        else return majorString;
//
//        StringTokenizer dobToken = new StringTokenizer(dobString);
//        int month = Integer.parseInt(dobToken.nextToken("/"));
//        int day = Integer.parseInt(dobToken.nextToken("/"));
//        int year = Integer.parseInt(dobToken.nextToken("/"));
//
//        Date dob = new Date(year, month, day);
//
//        Profile addedProfile = new Profile (fname, lname, dob);
//
//        student.setProfile(addedProfile);
//        student.setMajor(major);
//        student.setCreditsCompleted(creditsCompleted);
//        return majorString;
//    }

    /**
     * Checks if a given course is valid for the period given
     * @param courseString course we want to add
     * @param period period for the course we want to add
     * @return T if time slot is open, F if time slot is closed
     */
    private boolean isValidPeriodTime(String courseString, int period){
        Course c = Course.valueOf(courseString.toUpperCase());
        Time p = getPeriod(period);
        for(int i = 0; i < schedule.size(); i++){
            Section section = schedule.get(i);
            if(section.getPeriod().equals(p) && section.getCourse().equals(c)) return false;
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
        for(int j = 0; j < schedule.size(); j++){
            Section section = schedule.get(j);
            if(section.getInstructor().equals(i) && section.getPeriod().equals(p)) return false;
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

        for(int i = 0; i < schedule.size(); i++){
            Section section = schedule.get(i);
            if(section.getClassroom().equals(c) && section.getPeriod().equals(p)) return false;
        }
        return true;
    }

//    /**
//     * Is the student valid, ie does makestudent student
//     * @param student student to be checked
//     * @return T if valid, F if false
//     */
//    private boolean isValidStudent(String student){
//        Student currStudent = new Student();
//        makeStudent(student,currStudent);
//        if(currStudent.getSchoolYear().isEmpty())return false;
//        return true;
//    }

    /**
     * Finds section if it exists
     * @param courseString course code as a string
     * @param periodInt period as an int
     * @param schedule schedule
     * @return section if found
     * @throws Exception if section is not in schedule
     */
    private Section findSection(String courseString, int periodInt, Schedule schedule) throws Exception{
        Course course = Course.valueOf(courseString.toUpperCase());
        Time period = getPeriod(periodInt);
        Section section;
        //getting section if it exists
        for(int i = 0; i < schedule.size(); i++) //loop through sections
        {
            if(schedule.get(i).getCourse().equals(course) && schedule.get(i).getPeriod().equals(period)){
                section = schedule.get(i);
                return section;
            }
        }

        throw new Exception("INVALID: " + course.name() + " " + period.getStart() + " does not exist.");
    }

    private int getCurrCredits(Student student)
    {
        int creditCount = 0;
        //loop thru all sections
        for(int i = 0; i < schedule.size(); i++)
        {
            Section currSection = schedule.get(i);
            if(currSection.getRoster().contains((student)))
            {
                //if student is in section, increase the number of credits
                creditCount += currSection.getCourse().getCreditHours();
            }
        }
        return creditCount;
    }
    /*
===========================================================
                  END of Helper Functions
===========================================================
 */





}
