package registration;

public class International extends NonResident{
    private boolean isStudyAbroad;

    International(Profile profile, Major major, int creditsCompleted, boolean isStudyAbroad) {
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    /**
     * Overrides parent getType, returns type of student
     * @return String "International"
     */
    @Override
    public String getType(){ return "International";}
}
