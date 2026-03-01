package registration;

/**
 * Resident class that extends STUDENT class
 * @author Ysabella Llanos, Kevin Toan
 */
public class Resident extends Student{
    private int scholarship;
    final private double FULLTIME_TUITION = 14933;
    final private double PARTTIME_TUITION_PERCREDIT = 482;

    Resident(Profile profile,Major major, int creditsCompleted, int scholarship){
        super(profile,major, creditsCompleted);
        this.scholarship = scholarship;
    }
    int getScholarship(){ return scholarship; }

    /**
     * Add scholarship to this resident
     * @param scholarship amount wanted to add
     * @throws Exception total scholarship cannot > 10,000.
     */
    void addScholarship(int scholarship) throws Exception {
        int newScholarship = this.scholarship + scholarship;
        if(newScholarship > 10000) throw new Exception("Scholarship cannot exceed 10000");
        this.scholarship = newScholarship;
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
