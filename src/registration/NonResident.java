package registration;

/**
 Non-Resident class that extends Student class
 @author Ysabella Llanos, Kevin Toan
 */
public class NonResident extends Student{
    private final int FULLTIME_TUITION = 35758;
    private final int PARTTIME_TUITION_PERCREDIT = 1162;

    NonResident(Profile profile, Major major, int creditsCompleted){
        super(profile, major, creditsCompleted);
    }

    /**
     * Get type of student
     * @return String "NonResident"
     */
    public String getType(){return "[NonResident]";}

    /**
     * Caluclates tuition only accounts for tuition + university fee
     * @param creditsEnrolled # of credits enrolled
     * @return double tuition calculated
     */
    @Override
    public double tuition(int creditsEnrolled) {
        double finalTuition = 0;
        //part time student
        if(creditsEnrolled < 1){ return finalTuition;}
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
        return finalTuition;
    }
}
