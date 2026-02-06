package registration;

public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

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
     Compare the profiles to determine the order
     * @param profile the object to be compared.
     * @return 1, -1, or 0
     */
    @Override
    public int compareTo(Profile profile)
    {

       return 0; //if last names equal go to first
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
}
