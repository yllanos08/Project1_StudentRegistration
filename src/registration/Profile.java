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
            return (profile.fname.equals(this.fname) && profile.lname.equals(this.lname) && profile.dob.equals(this.dob));
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

    public static void main (String[] args)
    {

    }
}
