package registration;

import java.text.DecimalFormat;

/**
 * Resident class that extends STUDENT class
 * @author Ysabella Llanos, Kevin Toan
 */
public class Resident extends Student{
    private int scholarship;
    final private double FULLTIME_TUITION = 14933;
    final private double PARTTIME_TUITION_PERCREDIT = 482;

    /**
     * Constructor w/ all values
     * @param profile profile of student
     * @param major major of student
     * @param creditsCompleted # of credits completed
     * @param scholarship scholarship $ they have
     */
    Resident(Profile profile,Major major, int creditsCompleted, int scholarship){
        super(profile,major, creditsCompleted);
        this.scholarship = scholarship;
    }

    /**
     * Default constructor
     * @param profile profile of student
     * @param major major of student
     * @param creditsCompleted # of credits completed
     */
    Resident(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
        this.scholarship = 0;
    }

    /**
     * Getter for scholarship
     * @return int of current scholarship $
     */
    int getScholarship(){ return scholarship; }

    /**
     * Getter for Type of Student
     * @return String "Resident"
     */
    public String getType(){return "[Resident]";}
    /**
     * Add scholarship to this resident
     * @param scholarship amount wanted to add
     * @throws Exception total scholarship cannot > 10,000.
     */
    void setScholarship(int scholarship) throws Exception {
        if(scholarship > 10000) throw new Exception("Scholarship cannot exceed 10000");
        this.scholarship = scholarship;
    }


    /**
     Specialized toString for residents with a scholarship
     * @return Student toString + scholarship
     */
    @Override
    public String toString()
    {
        DecimalFormat df = new DecimalFormat("$#,##0");
        String formattedScholarship = df.format(this.scholarship);
        if(this.scholarship > 0) return super.toString() + " [scholarship: " + formattedScholarship + "]";
        return super.toString();
    }


    /**
     * Calculates tuition based on creditsEnrolled & tuition
     * 12+ = fulltime
     * <12 = part time
     * Any credits above 16 has additional cost per credit
     * @param creditsEnrolled amount of credits student is enrolled in
     * @return the amount of tuition for student
     */
    @Override
    public double tuition(int creditsEnrolled) {
        double finalTuition = 0;
        if(creditsEnrolled < 1) return finalTuition;
        //part time student
        if(creditsEnrolled < 12){
            finalTuition += creditsEnrolled * PARTTIME_TUITION_PERCREDIT;
            finalTuition += PARTTIME_UNIVERSITYFEE;
        }

        //full time student
        if(creditsEnrolled >= 12){
            finalTuition += FULLTIME_TUITION;
            finalTuition += FULLTIME_UNVERSITYFEE;
            if(creditsEnrolled > 16){
                int creditsOver = creditsEnrolled - 16;
                finalTuition += creditsOver * PARTTIME_TUITION_PERCREDIT;
            }
        }

        finalTuition -= scholarship;
        return finalTuition;
    }



}
