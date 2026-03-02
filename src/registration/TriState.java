package registration;

public class TriState extends NonResident{
    private String state;
    private final double NY_DISCOUNT = 4000;
    private final double CT_DISCOUNT = 5000;
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
    public String getType(){return "[Tristate: " + state + "]";}

    /**
     * Tristate's tuition calc, discounts for NY (4000) and CT (5000) students
     * @param creditsEnrolled # of credits student is enrolled in
     * @return double tuition value for student
     */
    @Override
    public double tuition(int creditsEnrolled){
        double finalTuition = super.tuition(creditsEnrolled);
        if(creditsEnrolled > 12){
            if(state.equalsIgnoreCase("NY")) finalTuition -= NY_DISCOUNT;
            else finalTuition -= CT_DISCOUNT;
        }
        return finalTuition;
    }
}
