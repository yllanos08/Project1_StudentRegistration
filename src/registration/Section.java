package registration;

/**
 Section class
 @author Kevin Toan, Ysabella Llanos
 */

public class Section {
    private final static int CAPACITY = 4; //sections only have max 4 students CANNOT BE INCREASED
    private Course course;
    private Instructor instructor;
    private Classroom classroom;
    private Time time; //time period
    private StudentList roster;
    private int numStudents;

    /**
     Parameterized constructor
     * @param course
     * @param instructor
     * @param classroom
     * @param time
     */
    Section(Course course, Instructor instructor, Classroom classroom, Time time){
        this.course = course;
        this.instructor = instructor;
        this.classroom = classroom;
        this.time = time;

        roster = new StudentList();
        numStudents = 0;
    }

    public Course getCourse(){return this.course;}
    public Instructor getInstructor(){return this.instructor;}
    public Classroom getClassroom(){return this.classroom;}
    public Time getPeriod() {return this.time;}
    public StudentList getRoster() {return this.roster;}

    public int getNumStudents(){return roster.size();}




    /**
     * enroll student into class if class isn't full (doesn't check requirements)
     * @param student - student TBE (to be enrolled)
     */

    public void enroll(Student student) throws Exception {

        if(isFull()) throw new Exception("Cannot enroll " + "[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " "
        + student.getProfile().getDob() + "]," + " " + this.getCourse() + " " + this.getPeriod().getStart() + " is full.");
        if(roster.contains(student)) throw new Exception("Student already in section");
        roster.add(student);
    }

    /**
     * drops student if they exist
     * @param student - student TBR (to be removed)
     */
    public void drop(Student student) throws Exception {
        if(!roster.contains(student)){
            throw new Exception ("[" + student.getProfile().getFname() + " " + student.getProfile().getLname() + " " + student.getProfile().getDob()
                                + "] " + "is not enrolled in this section.");
        }
        roster.remove(student);
    }

    /**
     * Checks if section is full
     * @return TRUE if full, False otherwise
     */
    public boolean isFull() {
        return roster.size() == CAPACITY;
    }

    /**
     * prints out roster of the section
     */
    public void print() {
        if(roster.isEmpty()) System.out.println("** No students Enrolled **");
        else{
            System.out.println("** Roster **");
            for(int i = 0; i < roster.size(); i++){
                Student currStudent = roster.get(i);
                System.out.println("    [" +  currStudent.getProfile().getFname() + " " + currStudent.getProfile().getLname() + " " + currStudent.getProfile().getDob() + "]");
            }
        }

    }

    /**
     Compare 2 Section objects to see if they are equal.
     * @param obj   the reference object with which to compare.
     * @return true if period and major prereqs are the same; false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Section){
            Section s = (Section) obj;
            return this.time.getStart().equals(s.time.getStart()) &&
                    this.course.getMajorPrereq().equals(s.course.getMajorPrereq()) &&
                    this.instructor.equals(s.instructor) &&
                    this.classroom.equals(s.classroom);
        }
        return false;
    }

    /**
     Return a string including all values
     * @return textual representation of the Section
     */
    @Override
    public String toString(){
        return "[" + course.getMajorPrereq() + " " + time.getStart() + "]" + " "
                + "[" + instructor + "]" + " "
                + "[" + classroom + ", " + classroom.getBuilding() + ", " + classroom.getCampus();
   }
}
