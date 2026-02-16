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
        this.numSections += CAPACITY;
        Section [] newList = new Section [numSections];
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
     Remove section from list of sections
     * @param section section to be removed
     */
    public void remove(Section section)
    {
        if(!this.contains(section)) return;
        Section lastSection = this.sections[numSections -1]; //get last section

        //replace last with what we want to remove

        int index = this.find(section);
        this.sections[numSections - 1] = null;
        this.sections[index] = lastSection;
    }

    /**
     Enroll a student into a section
     * @param section section that student will be enrolled in
     * @param student student to be enrolled
     */
    public void enroll(Section section, Student student) {
        //checks to do: credit count, method done
        //time conflict, method done
        //not alr enrolled in class just diff section
        //pre reqs are met (major and year)

        //do nothing if max credits will be exceeded, prereqs not met, time conflict, and alr enrolled
        if(checkCreditCount(student) + section.getCourse().getCreditHours() > 18) return; //if max credits exceeded
        if(duplicateCourse(section, student)) return;
        if(checkStudentTimeConflict(section, student)) return;
        if(metPrereq(section, student))
        {
            section.enroll(student);
        }
    }
    /**
     Drop a student from a section
     * @param section section that student will be removed from
     * @param student student to be removed
     */
    public void drop(Section section, Student student)
    {
        int indexOfSection = find(section);
        this.sections[indexOfSection].drop(student);
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
        for(Section s: this.sections) //print now that it is sorted
        {
            System.out.print(s+ " ");
        }
    }
    public void printByCourse()
    {
        //selection sort by course#, then period
        for(int i = 0; i < numSections; i++)
        {
            int swapIndex = i;
            Section smallestSection = this.sections[i];
            for(int j = i + 1; j < this.numSections; j++)
            {
                //compare by coursenumber first

                //if equal go by building
                //collegeave.compareTo(busch) will return 1
                int courseCompare = smallestSection.getCourse().compareTo(this.sections[j].getCourse());
                if(courseCompare > 0)
                {
                    swapIndex = j;
                    smallestSection = this.sections[j];
                }
                if(courseCompare == 0)
                {
                    //check building
                    if(smallestSection.getTime().compareTo(this.sections[j].getTime()) > 0)
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
            if(currSection.getTime().equals(section.getTime())) //if period matches
            {
                //check if student is in roster
                if(currSection.contains(student)) return true;
            }
        }
        return false;
    }

    /**
     Check if the instructor has a time conflict with the given section
     * @param section section to be taught
     * @param instructor instructor being checked
     * @return true if there is a time conflict, false otherwise
     */
    private boolean instructorTimeConflict(Section section, Instructor instructor)
    {
        for(int i = 0; i < numSections; i++)
        {
            Section currSection = this.sections[i];
            if(currSection.getTime().equals(section.getTime()))
            {
                if(currSection.getInstructor().equals(instructor)) return true;
            }
        }
        return false;
    }

    /**
     Check if a student meets the prereqs for a section (major and year)
     * @param section section to check prereqs
     * @param student student that must meet prereqs
     * @return true if student meets prereqs, false otherwise
     */
    private boolean metPrereq(Section section, Student student)
    {

        //major prereq
        String majorReq = section.getCourse().getMajorPrereq();
        if(!majorReq.equals("N/A") && !majorReq.equals(student.getMajor().name()))
        {
            return false;
        }

        int threshold = 0;
        String yearReq = section.getCourse().getYearPrereq();
        //get year req in terms ofc credit
        if(yearReq.equals("Freshman")) threshold = FRESHMAN;
        else if(yearReq.equals("Sophomore")) threshold = SOPHOMORE;
        else threshold = JUNIOR;

        return(student.getCreditsCompleted() > threshold);
    }

    /**
     Check if student is enrolled in section of same course
     * @param section section student is to be enrolled in
     * @param student student to be enrolled
     * @return true if student is already enrolled in section with same course number, false otherwise
     */
    private boolean duplicateCourse(Section section, Student student)
    {
        String courseNumber = section.getCourse().name();
        for(int i = 0; i < numSections; i++)
        {
            if(courseNumber.equals(this.sections[i].getCourse().name()))
            {
                if(this.sections[i].contains(student)) return true;
            }
        }
        return false;
    }




}


