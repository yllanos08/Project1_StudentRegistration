package registration;

public class TriState extends NonResident{
    private String state;
    TriState(Profile profile, Major major, int creditsCompleted, String state) throws Exception {
        super(profile, major, creditsCompleted);
        if(!state.equalsIgnoreCase("NY") && !state.equalsIgnoreCase("CT"))
            throw new Exception(state + ": invalid state code.");
        this.state = state;
    }

    /**
     * Overrides parent getType, gets type of student
     * @return String "Tristate: state"
     */
    @Override
    public String getType(){return "Tristate: " + state;}
}
