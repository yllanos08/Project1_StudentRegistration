package registration;

public class Prerequisite {
    private String year;
    private String major;

    public Prerequisite(String year) {
        this.year = year;
    }
    public Prerequisite(String year, String major){
        this.year = year;
        this.major = major;
    }
}
