package registration;

/**
 Schedule class
 @author Ysabella Llanos, Kevin Toan
 */
public class Schedule
{
    private static final int NOT_FOUND = -1;
    private static final int CAPACITY = 4;
    private Section[] sections; // all the sections
    private int numSections;

    private final int FRESHMAN = 30;
    private final int SOPHOMORE = 60;
    private final int JUNIOR = 90;


    /**
     Constructor
     */
    Schedule()
    {
        this.numSections = 0;
        this.sections = new Section[CAPACITY];
    }

    public int getNumSections(){return numSections;}
    /**
     * get method for sections
     * @return list of sections in this.schedule
     */
    public Section[] getSections(){return sections;}
    /**
     Find a section in our current list
     * @param section section to be found
     * @return the index of section in the list, -1 otherwise
     */
    private int find(Section section)
    {
        for(int i = 0; i < this.sections.length; i++)
        {
            if(section.equals(this.sections[i])) return i;
        }
        return NOT_FOUND;
    }

    /**
     Determines if the current section list is full
     * @return true if at max capacity, false otherwise
     */
    private boolean isFull()
    {
        return this.numSections == this.sections.length;
    }

    /**
     Increase capacity by 4
     */
    private void grow()
    {
        Section [] newList = new Section [this.sections.length + CAPACITY];
        for(int i = 0; i < this.sections.length; i++)
        {
            newList[i] = this.sections[i];
        }
        this.sections = newList;
    }

    /**
     Add section to list of sections
     * @param section section to be added
     */
    public void add(Section section)
    {
        if(isFull())
        {
            this.grow();
        }
        sections[numSections++] = section;
    }

    /**
     Tries to remove section from schedule (closing cmd)
     checks if section is empty first
     * @param section section to be closed
     * @throws Exception if section is empty
     */
    public void remove(Section section) throws Exception
    {
        if (section.getNumStudents() > 0) {
            throw new Exception(section.getCourse() + " " + section.getPeriod().getStart() + " cannot be removed "
                    + "[" + section.getNumStudents() + " student(s) enrolled" + "]");
        }
        Section lastSection = this.sections[numSections - 1]; //get last section

        //replace last with what we want to remove

        int index = this.find(section);
        this.sections[numSections--] = null;
        this.sections[index] = lastSection;
    }

    /**
     Enroll a student into a section
     * @param section section that student will be enrolled in
     * @param student student to be enrolled
     */
    public void enroll(Section section, Student student) throws Exception {
        try{
            if(checkCreditCount(student) + section.getCourse().getCreditHours() > 18) {
                throw new Exception("Cannot enroll [" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() +
                        "]; " + "now has " + checkCreditCount(student) + " will exceed credit limit of 18"); //if max credits exceeded
            }
            if(duplicateCourse(section, student)){
                throw new Exception("[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() +  "]"
                        + " already enrolled in " + section.getCourse());
            }
            if(checkStudentTimeConflict(section, student)) {
                String period = section.getPeriod().name();
                String periodInt = period.substring(6);
                throw new Exception("Time conflict: " + "[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() +  "]"
                        + "enrolled in another class at period " + periodInt);
            }

            //metPrereq will throw its own exception
            metPrereq(section, student);
            section.enroll(student);
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }
    /**
     Drop a student from a section
     * @param section section that student will be removed from
     * @param student student to be removed
     */
    public void drop(Section section, Student student) throws Exception
    {
        int indexOfSection = find(section);
        try{
            this.sections[indexOfSection].drop(student);
        }
        catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }

    /**
     Check if section is in schedule
     * @param section section to be found
     * @return true if section is in schedule, false otherwise
     */
    public boolean contains(Section section)
    {
        if(this.find(section) == NOT_FOUND) return false;
        return true;
    }

    public void printByClassroom()
    {
        if(this.numSections == 0){
            System.out.println("Schedule is empty!");
            return;
        }
        //selection sort
        for(int i = 0; i < this.numSections; i++)
        {
            int swapIndex = i;
            Section smallestSection = this.sections[i];
            for(int j = i + 1; j < this.numSections; j++)
            {
                //compare by campus first

                //if equal go by building
                //collegeave.compareTo(busch) will return 1
                int campusCompare = smallestSection.getClassroom().getCampus().compareTo(this.sections[j].getClassroom().getCampus());
                if(campusCompare > 0)
                {
                    swapIndex = j;
                    smallestSection = this.sections[j];
                }
                if(campusCompare == 0)
                {
                    //check building
                    if(smallestSection.getClassroom().getBuilding().compareTo(this.sections[j].getClassroom().getBuilding()) > 0)
                    {
                        swapIndex = j;
                        smallestSection = this.sections[j];

                    }
                }
            }
            Section temp = this.sections[i];
            this.sections[i] = this.sections[swapIndex];
            this.sections[swapIndex] = temp;


        }
        for(int i = 0; i < numSections; i++){
            Section currSection = sections[i];
            System.out.println("[" + currSection.getCourse() + " " + currSection.getPeriod().getStart() + "]"
                    + " "  + "[" + currSection.getInstructor().name().toUpperCase() + "]" +
                    " " + "[" + currSection.getClassroom() + ", " + currSection.getClassroom().getBuilding() + ", " + currSection.getClassroom().getCampus() + "]");
            currSection.print();
        }
    }
    public void printByCourse()
    {
        System.out.println("* List of sections ordered by course number, section time * ");
        if(numSections == 0){
            System.out.println("Schedule is empty!");
            return;
        }
        //selection sort by course#, then period
        for(int i = 0; i < numSections; i++)
        {
            int swapIndex = i;
           // Section smallestSection = this.sections[i];
            for(int j = i + 1; j < this.numSections; j++) {
                int courseCompare = sections[j].getCourse().name().compareTo(sections[swapIndex].getCourse().name());;

                if (courseCompare < 0) swapIndex = j;
                else if (courseCompare == 0) {
                    if (sections[j].getPeriod().compareTo(sections[swapIndex].getPeriod()) < 0) swapIndex = j;
                }
            }
            Section temp = sections[i];
            sections[i] = sections[swapIndex];
            sections[swapIndex] = temp;
        }
        for(int i = 0; i < numSections; i++){
            Section currSection = sections[i];

            System.out.println("[" + currSection.getCourse() + " " + currSection.getPeriod().getStart() + "]"
                    + " "  + "[" + currSection.getInstructor().name().toUpperCase() + "]" +
                    " " + "[" + currSection.getClassroom() + ", " + currSection.getClassroom().getBuilding() + ", " + currSection.getClassroom().getCampus() + "]");
            currSection.print();
        }
        System.out.println("* end of list *");
    }


    //DELETE THIS AFTER BUT THESE ARE ALL THE CHECKS BEFORE ENROLLMENT--------------------- (time conflict, max credit, alr enrolled in same class, major and year prereq)
    /**
     Check the number of credits a student is currently taking in their current schedule
     * @param student student we are checking the credits of
     * @return number of credits a student is currently taking
     */
    private int checkCreditCount(Student student)
    {
        int creditCount = 0;
        //loop thru all sections
        for(int i = 0; i < numSections; i++)
        {
            Section currSection = this.sections[i];
            if(currSection.contains((student)))
            {
                //if student is in section, increase the number of credits
                creditCount += currSection.getCourse().getCreditHours();
            }
        }
        return creditCount;
    }

    /**
     Check if a student has a time conflict with given section
     @param section section to check conflict
     @param student student to check conflict
     @return true if there is a time conflict, false otherwise
     */
    private boolean checkStudentTimeConflict(Section section, Student student)
    {
        for(int i = 0; i < numSections; i++)
        {
            Section currSection = this.sections[i];
            if(currSection.getPeriod().equals(section.getPeriod())) //if period matches
            {
                //check if student is in roster
                if(currSection.contains(student)) return true;
            }
        }
        return false;
    }


    /**
     Checks if a student has met the prereqs for the section
     * @param section section to be checked
     * @param student student to be checked
     * @throws Exception throws exceptions for not meeting major/year prereqs
     */
    private void metPrereq(Section section, Student student) throws Exception
    {
        //major prereq
        String majorReq = section.getCourse().getMajorPrereq();
        if(!majorReq.equals("N/A") && !majorReq.equals(student.getMajor().name()))
        {
            throw new Exception("Prereq: major only - " + "[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() +  "]"
                    + "[" + student.getMajor() + "]");
        }

        int threshold = 0;
        String yearReq = section.getCourse().getYearPrereq();
        //get year req in terms ofc credit
        if (yearReq.equals("Freshman")) {
        } else if (yearReq.equals("Sophomore")) {
            threshold = FRESHMAN; // Must have at least 30
        } else if (yearReq.equals("Junior")) {
            threshold = SOPHOMORE; // Must have at least 60
        } else if (yearReq.equals("Senior")) {
            threshold = JUNIOR; // Must have at least 90
        }

        //need
        if(!(student.getCreditsCompleted() >= threshold))
        {
            throw new Exception("Prereq: " + section.getCourse().getYearPrereq() + " - " + "[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob() +  "]"
                    + "[" + student.getSchoolYear() + "]");
        }
    }

    /**
     Check if student is enrolled in section of same course
     * @param section section student is to be enrolled in
     * @param student student to be enrolled
     * @return true if student is already enrolled in section with same course number, false otherwise
     */
    private boolean duplicateCourse(Section section, Student student)
    {

        Course checkCourse = section.getCourse();
        for(int i = 0; i < numSections; i++)
        {
            if(checkCourse.equals(this.sections[i].getCourse()))
            {
                Course newCourse = this.sections[i].getCourse();

                if(this.sections[i].contains(student)) return true;
            }
        }
        return false;
    }




}


