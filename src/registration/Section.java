package registration;

/**
 Section class
 @author Kevin Toan, Ysabella Llanos
 */

import java.util.StringTokenizer;

public class Section {
    private final static int CAPACITY = 4; //sections only have max 4 students CANNOT BE INCREASED
    private Course course;
    private Instructor instructor;
    private Classroom classroom;
    private Time time; //time period
    private Student[] roster;
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

        roster = new Student[CAPACITY];
        numStudents = 0;
    }

    public Course getCourse(){return this.course;}
    public Instructor getInstructor(){return this.instructor;}
    public Classroom getClassroom(){return this.classroom;}
    public Time getTime() {return this.time;}
    public Student[] getRoster() {return this.roster;}




    /**
     * enroll student into class if class isn't full (doesn't check requirements)
     * @param student - student TBE (to be enrolled)
     */
    public void enroll(Student student) {
        if(isFull()) return;
        if(contains(student)) return;
        roster[numStudents++] = student;
    }

    /**
     * drops student if they exist
     * @param student - student TBR (to be removed)
     */
    public void drop(Student student) {
        if(!contains(student)) return;
        int indexOfStudent = find(student);
        //remove and replace
        Student lastStudent = roster[numStudents - 1];
        roster[indexOfStudent] = lastStudent;
        roster[numStudents] = null;
        numStudents--;
    }

    /**
     * Check if student is in roster
     * @param student student TBF (to be found)
     * @return TRUE if student found, FALSE otherwise
     */
    public boolean contains(Student student) {
        for(Student s: roster){
            if(s.equals(student)) return true;
        }
        return false;
    }

    /**
     * Finds index of student
     * @param student student TBF (to be found)
     * @return index of student in roster
     */
    public int find(Student student){
        for(int i = 0; i < numStudents; i++){
            if(roster[i].equals(student)) return i;
        }
        return -1;
    }

    /**
     * Checks if section is full
     * @return TRUE if full, False otherwise
     */
    public boolean isFull() {
        return numStudents == CAPACITY;
    }

    /**
     * prints out roster of the section
     */
    public void print() {
        for(Student s: roster){
            System.out.println(s.toString());
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
                    this.course.getMajorPrereq().equals(s.course.getMajorPrereq());
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
