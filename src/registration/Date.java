package registration;
import java.util.Calendar;

/**
 Date class
 @author Ysabella Llanos, Kevin Toan
 */
public class Date implements Comparable <Date>
{
    private int year;
    private int month;
    private int day;
    private Calendar rightNow = Calendar.getInstance();
    private int currYear = rightNow.get(Calendar.YEAR);
    private int currMonth = rightNow.get(Calendar.MONTH) + 1;
    private int currDate = rightNow.get(Calendar.DATE);

    public static final int JAN = Calendar.JANUARY +1;
    public static final int FEB = Calendar.FEBRUARY + 1;
    public static final int MAR = Calendar.MARCH + 1;
    public static final int APR = Calendar.APRIL + 1;
    public static final int MAY = Calendar.MAY + 1;
    public static final int JUN = Calendar.JUNE + 1;
    public static final int JUL = Calendar.JULY + 1;
    public static final int AUG = Calendar.AUGUST + 1;
    public static final int SEP = Calendar.SEPTEMBER + 1;
    public static final int OCT = Calendar.OCTOBER + 1;
    public static final int NOV = Calendar.NOVEMBER +1;
    public static final int DEC = Calendar.DECEMBER + 1;

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUATERCENTENNIAL = 400;


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

    public int getYear(){return this.year;}
    public int getMonth(){return this.month;}
    public int getDay(){return this.day;}

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

    /**
     Check if the year is a leap year
     * @return true if leap year, false otherwise.
     */
    public boolean isLeap()
    {
        if(this.year % QUADRENNIAL == 0)
        {
            if(this.year % CENTENNIAL == 0)
            {
                return this.year % QUATERCENTENNIAL == 0;
            }
            else
            {
                return true;
            }
        }
        return false;
    }
    /**

     * @return true if the given date is valid, false otherwise
     */
    public boolean isValid() {
        if (this.day < 1 || this.day > 31) return false;
        if (this.month < JAN || this.month > DEC) return false;
        if (this.day == 29 && this.month == FEB) return isLeap();
        if (this.day == 31 && (this.month == FEB || this.month == APR
                || this.month == JUN || this.month == SEP || this.month == NOV)) return false;
        if (this.day == 30 && this.month == FEB) return false;

        return true;
    }


    /**
     Testbed to test isValid()
     * @param args
     */
    public static void main (String[] args)
    {
        Date d1 = new Date (2005, 1, 1);
        System.out.println("CURRENT DATE IS: " + d1.currMonth + "/" + d1.currDate + "/" + d1.currYear);
        System.out.println(d1.toString());

        System.out.println("isValid() testing, [FALSE] test cases (4):");
        //invalid 1: 2/29/25 (non leap year)
        Date d2 = new Date (2025, 2, 29);
        System.out.println(d2.isValid());

        //invalid 2: 1/32/25 (extra 1/32 is not a valid date)
        Date d3 = new Date (2025, 1, 32);
        System.out.println(d3.isValid());

        //invalid 3: 6/31/25 (6/31 is not a valid date, non 31 month)
        Date d4 = new Date (2025, 6, 31);
        System.out.println(d4.isValid());

        //invalid 4: 13/30/25 (13 is not a valid month, not valid month)
        Date d5 = new Date (2025, 13, 30);
        System.out.println(d5.isValid());

        System.out.println("[TRUE] test cases (2):");
        //valid 1: 2/29/24, valid leap year
        Date d6 = new Date (2024, 2, 29);
        System.out.println(d6.isValid());

        //valid 2: 12/31/25, valid date
        Date d7 = new Date(2025, 12, 31);
        System.out.println(d7.isValid());

    }
}

