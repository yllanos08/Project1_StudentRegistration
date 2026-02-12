package registration;

public enum Classroom {
    HIL114 ("Hill Center", "Busch"),
    ARC103 ("Allison Road Classroom", "Busch"),
    BEAUD ("Beck Hall", "Livingston"),
    TIL232 ("Tillet Hall", "Livingston"),
    AB2225 ("Academic Building", "College Avenue"),
    MU302 ("Murray Hall", "College Avenue");

    public final String building;
    public final String campus;

    private Classroom(String building, String campus)
    {
        this.building = building;
        this.campus = campus;
    }

    public String getBuilding(){
        return building;
    }
    public String getCampus(){
        return campus;
    }
}
