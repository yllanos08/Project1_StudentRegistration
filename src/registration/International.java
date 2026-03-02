package registration;

public class International extends NonResident{
    private boolean isStudyAbroad;

    International(Profile profile, Major major, int creditsCompleted, boolean isStudyAbroad) {
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

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
