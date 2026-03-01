package registration;
import util.List;
import util.Sort;
/**
 Schedule class
 @author Ysabella Llanos, Kevin Toan
 */
public class Schedule extends List<Section>
{
    private final int FRESHMAN = 30;
    private final int SOPHOMORE = 60;
    private final int JUNIOR = 90;

    /**
     Default Constructor
     */
    Schedule()
    {
        super();
    }

    /**
     Remove section from schedule
     * @param section section to be removed
     */
    @Override
    public void remove(Section section)
    {
        if (section.getNumStudents() > 0) {
            throw new IllegalArgumentException(section.getCourse() + " " + section.getPeriod().getStart() + " cannot be removed "
                    + "[" + section.getNumStudents() + " student(s) enrolled" + "]");
        }
        super.remove(section);
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
        int indexOfSection = this.indexOf(section);
        try{
            this.get(indexOfSection).drop(student);
        }
        catch (Exception exception){
            throw new Exception(exception.getMessage());
        }

    }

    /**
     Print schedule of classes sorted by campus then building
     */
    public void printByClassroom()
    {
        System.out.println("* List of sections ordered by campus, building * ");
        if(this.size() == 0){
            System.out.println("Schedule is empty!");
            return;
        }

        Sort.locSort(this);

        for(int i = 0; i < this.size(); i++){
            Section currSection = this.get(i);
            System.out.println("[" + currSection.getCourse() + " " + currSection.getPeriod().getStart() + "]"
                    + " "  + "[" + currSection.getInstructor().name().toUpperCase() + "]" +
                    " " + "[" + currSection.getClassroom() + ", " + currSection.getClassroom().getBuilding() + ", " + currSection.getClassroom().getCampus() + "]");
            currSection.print();
        }
    }

    /**
     Print schedule of classes by course number then period
     */
    public void printByCourse()
    {
        System.out.println("* List of sections ordered by course number, section time * ");
        if(this.size() == 0){
            System.out.println("Schedule is empty!");
            return;
        }

        Sort.courseSort(this);
        for(int i = 0; i < this.size(); i++){
            Section currSection = this.get(i);

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
        for(int i = 0; i < this.size(); i++)
        {
            Section currSection = this.get(i);
            if(currSection.getRoster().contains((student)))
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
        for(int i = 0; i < this.size(); i++)
        {
            Section currSection = this.get(i);
            if(currSection.getPeriod().equals(section.getPeriod())) //if period matches
            {
                //check if student is in roster
                if(currSection.getRoster().contains(student)) return true;
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
        for(int i = 0; i < this.size(); i++)
        {
            if(checkCourse.equals(this.get(i).getCourse()))
            {
                Course newCourse = this.get(i).getCourse();

                if(this.get(i).getRoster().contains(student)) return true;
            }
        }
        return false;
    }
}
