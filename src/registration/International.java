package registration;

/**
 International class that extends Non-Resident class
 @author Ysabella Llanos, Kevin Toan
 */
public class International extends NonResident{
    private final double ADMIN_FEE = 500;
    private final double HEALTHINSURANCE_FEE = 2650;
    private boolean isStudyAbroad;


    International(Profile profile, Major major, int creditsCompleted, boolean isStudyAbroad) {
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     Checks if the international student is studying abroad
     * @return true if student is studying abroad, false otherwise
     */
    public boolean isAbroad(){return isStudyAbroad;}

    /**
     * Overrides parent getType, returns type of student
     * @return String "International"
     */
    @Override
    public String getType(){
        if(isStudyAbroad) return "[International study abroad]";
        else return "[International]";
    }

    /**
     * Tuition calc for international students;
     * STUDY ABROAD DOES NOT PAY TUITION!
     * @param creditsEnrolled #of credits enrolled
     * @return double tuition value
     */
    @Override
    public double tuition(int creditsEnrolled){
        double finalTuition = 0;
        if(!isStudyAbroad) finalTuition = super.tuition(creditsEnrolled);
        else {
            finalTuition += FULLTIME_UNVERSITYFEE;
        }
        finalTuition += ADMIN_FEE;
        finalTuition += HEALTHINSURANCE_FEE;
        return finalTuition;
    }

}
