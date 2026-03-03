package registration;

/**
 Enum class of possible Majors
 @author Ysabella Llanos, Kevin Toan
 */

public enum Major {
    CS ("School of Arts & Sciences"),
    ECE ("School of Engineering"),
    MATH ("School of Arts & Sciences"),
    ITI ("School of Communication and Information"),
    BAIT ("Rutgers Business School");

    private final String school;

    Major(String school) {
        this.school = school;
    }

    public String getSchool(){
        return this.school;
    }

    /**
     * Converts String to Major enum
     * @param majorString major that you want
     * @return Major obj of String
     * @throws Exception if Major does not exist
     */
    public static Major fromString(String majorString) throws Exception{
        try{
            return Major.valueOf(majorString.toUpperCase());
        }catch(IllegalArgumentException e){
            throw new Exception("INVALID: " + majorString + " major doesn't exist.");
        }
    }
}
