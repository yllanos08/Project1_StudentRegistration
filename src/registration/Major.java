package registration;

public enum Major {
    CS ("School of Arts & Sciences"),
    ECE ("School of Engineering"),
    MATH ("School of Arts & Sciences"),
    ITI ("School of Communication and Information"),
    BAIT ("Rutgers Business School");

    private String school;

    Major(String school){
        this.school = school;
    }

}
