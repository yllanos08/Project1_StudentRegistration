package registration;

public class Date implements Comparable <Date>
{
    private int year;
    private int month;
    private int day;

    /**
        Parameterized constructor with 3 arguments.
     * @param year
     * @param month
     * @param day
     */
    public Date (int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     Compare two Date objects and determine if they are equal
     * @param obj   the reference object with which to compare.
     * @return return true if year, month, and date are the same; false otherwise
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Date)
        {
            Date date = (Date) obj;
            return this.year == date.year && this.month == date.month && this.day == date.day;
        }
        return false;
    }

    /**
     Compare dates to determine the order
     * @param date the object to be compared.
     * @return 1, -1, or 0
     */
    @Override
    public int compareTo(Date date)
    {
        if (this.year > date.year) return 1;
        if (this.year < date.year) return -1;
        else {
            if(this.month > date.month) return 1;
            if(this.month < date.month) return -1;
            else{
                if(this.day > date.day) return 1;
                if(this.day < date.day) return -1;
            }
        }

        return 0;
    }

    /**
     Return a string including all values
     * @return a textual representation of object
     */
    @Override
    public String toString()
    {
        return month + "/" + day + "/" + year;
    }


}

