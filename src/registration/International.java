package registration;

/**
 International class that extends Non-Resident class
 @author Ysabella Llanos, Kevin Toan
 */
public class International extends NonResident{
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

}
