package registration;

public class Student implements Comparable<Student> {
    private Profile profile;
    private Major major;
    private int creditsCompleted;

    Student(Profile profile, Major major, int creditsCompleted){
        this.profile = profile;
        this.major = major;
        this.creditsCompleted = creditsCompleted;
    }


    /**
     * Compare 2 Student Objects to see if they are equal.
     * @param obj   the reference object with which to compare.
     * @return
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
        return this.profile.toString() + " " + this.major.toString() + " " + this.creditsCompleted;
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
