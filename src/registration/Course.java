package registration;

import java.util.StringTokenizer;

public enum Course {
    CS100(4, "Freshman"),
    CS200(4, "Sophomore"),
    CS300(4, "Junior", "CS major only"),
    CS400(4, "Junior", "CS major only"),
    CS442(3, "Junior"),
    PHY100(5, "Freshman"),
    PHY200(5, "Sophomore"),
    ECE300(4, "Junior", "ECE major only"),
    ECE400(4, "Senior", "ECE major only"),
    CCD(4, "Freshman"),
    HST(3, "Freshman");

    private final int creditHours;
    private final String yearPrereq;
    private final String majorPrereq;

    Course(int creditHours, String yearPrereq, String majorPrereq) {
        this.creditHours = creditHours;
        this.yearPrereq = yearPrereq;
        this.majorPrereq = majorPrereq;
    }

    Course(int creditHours, String yearPrereq) {
        this.creditHours = creditHours;
        this.yearPrereq = yearPrereq;
        this.majorPrereq = "N/A";
    }

    public int getCreditHours() {
        return creditHours;
    }

    public String getYearPrereq() {
        return yearPrereq;
    }

    /**
     *
     * @return only the major abbreviation
     */
    public String getMajorPrereq() {
        if (this.majorPrereq.equals("N/A")) return new String("N/A");
        StringTokenizer s = new StringTokenizer(this.majorPrereq);
        return s.nextToken();
    }


}
