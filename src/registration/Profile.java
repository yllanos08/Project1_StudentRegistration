package registration;

/**
 Profile class
 @author Ysabella Llanos, Kevin Toan
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;



    public Profile (String fname, String lname, Date dob)
    {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    public String getFname(){
        return fname;
    }
    public String getLname(){
        return lname;
    }
    public Date getDob(){
        return dob;
    }
    /**
     Compare two Profile objects and determine if they are equal.
     * @param obj   the reference object with which to compare.
     * @return true if their name and dob are the same; otherwise, return false.
     */
    @Override
    public boolean equals(Object obj)
    {
        if(obj instanceof Profile)
        {
            Profile profile = (Profile) obj;
            return (profile.fname.equalsIgnoreCase(this.fname) && profile.lname.equalsIgnoreCase(this.lname) && profile.dob.equals(this.dob));
        }
        return false;
    }

    /**
     Compare the profiles to determine the order based on last name -> first name -> then DOB
     * @param profile the object to be compared.
     * @return 1, -1, or 0
     */
    @Override
    public int compareTo(Profile profile)
    {
        if(this.lname.compareTo(profile.lname) > 0) return 1;
        if(this.lname.compareTo(profile.lname) < 0) return -1;
        else {
            if(this.fname.compareTo(profile.fname) > 0) return 1;
            if(this.fname.compareTo(profile.fname) < 0) return -1;
            else {
                if(this.dob.compareTo(profile.dob) > 0) return 1;
                if(this.dob.compareTo(profile.dob) <0 ) return -1;
            }
        }
       return 0;
    }

    /**
     Return a string including all values
     * @return a textual representation of the object
     */
    @Override
    public String toString()
    {
        return lname + ", " + fname + ", " + dob;
    }

    /**
     Testbed to test compareTo()
     * @param args
     */
    public static void main (String[] args)
    {
        //3 test cases -1, 3 test cases 1, 1 test case 0
        // last name diff, first name diff, dob diff (for both)
        Profile p1 = new Profile("Adam", "Scott", new Date(2005,4,8));
        Profile p2 = new Profile("Adam", "Tott", new Date(2005,4,8));
        Profile p3 = new Profile("Bob", "Scott", new Date(2005, 4, 8));
        Profile p4 = new Profile("Adam", "Scott", new Date(2005, 4, 9));
        Profile p5 = new Profile("Adam", "Scott", new Date(2005,4,8));

        System.out.println("Starting Test Cases...");
        System.out.println("[EXPECTED OUTPUT: -1]");

        System.out.println(p1.compareTo(p2));
        System.out.println(p1.compareTo(p3));
        System.out.println(p1.compareTo(p4));

        System.out.println("[EXPECTED OUTPUT: 1]");

        System.out.println(p2.compareTo(p1));
        System.out.println(p3.compareTo(p1));
        System.out.println(p4.compareTo(p1));

        System.out.println("[EXPECTED OUTPUT: 0]");
        System.out.println(p1.compareTo(p5));

    }
}
