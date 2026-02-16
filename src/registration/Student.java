package registration;

/**
 * Student obj with Profile, major, & credits completed
 * @author Kevin Toan, Ysabella Llanos
 */

public class Student implements Comparable<Student> {
    private Profile profile;
    private Major major;
    private int creditsCompleted;

    private final int FRESHMAN = 30;
    private final int SOPHOMORE = 60;
    private final int JUNIOR = 90;

    Student(Profile profile, Major major, int creditsCompleted){
        this.profile = profile;
        this.major = major;
        this.creditsCompleted = creditsCompleted;
    }
    public Student(){}

    public void setProfile(Profile profile){this.profile = profile;}
    public void setMajor(Major major){this.major = major;}
    public void setCreditsCompleted(int creditsCompleted){this.creditsCompleted = creditsCompleted;}

    public Profile getProfile(){
        return profile;
    }
    public Major getMajor(){
        return major;
    }
    public int getCreditsCompleted(){
        return creditsCompleted;
    }

    /**
     * finds school year of student < 30 = fresh, < 60 = soph, <90 = junior, 90+ = senior
     * @return school year of student in a String
     */
    public String schoolYear(){
        if(creditsCompleted > JUNIOR) return "Senior";
        else if(creditsCompleted > SOPHOMORE) return "Junior";
        else if(creditsCompleted > FRESHMAN) return "Sophomore";
        else return "Freshman";
    }
    /**
     * Compare 2 Student Objects to see if they are equal.
     * @param obj   the reference object with which to compare.
     * @return true if major and creditsCompleted are equal; false otherwise
     */
    @Override
    public boolean equals(Object obj){
       if(obj instanceof  Student){
           Student s = (Student) obj;
           return (this.profile.equals(s.profile) &&
                   this.major == s.major &&
                   this.creditsCompleted == s.creditsCompleted);
       }
       return false;
    }

    /**
     * makes student obj into str
     * format: profile + major + creditscompleted
     * @return string
     */

    @Override
    public String toString(){
        return "[" + profile.getFname() + " " + profile.getLname() + "]" + " "
                + "[" + major + "," + major.getSchool() + "]" + " "
                + "credits earned: " + creditsCompleted + "[" + schoolYear() + "]";
    }

    /**
     * Compares student obj in order profile -> major -> creditscompleted
     * @param s the object to be compared.
     * @return 1 if left obj >, -1 if left obj < , 0 if equal
     */
    @Override
    public int compareTo(Student s){
        if(this.profile.compareTo(s.profile) > 0) return 1;
        if(this.profile.compareTo(s.profile) < 0) return -1;
        else{
            if(this.major.compareTo(s.major) > 0) return 1;
            if(this.major.compareTo(s.major) < 0) return -1;
            else{
                if(this.creditsCompleted > s.creditsCompleted) return 1;
                if(this.creditsCompleted < s.creditsCompleted) return -1;
            }
        }
        return 0;
    }


}
