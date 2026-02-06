package registration;

public enum Locations {
    HIL114 ("Hill Center", "Busch"),
    ARC103 ("Allison Road Classroom", "Busch"),
    BEAUD ("Beck Hall", "Livingston"),
    TIL232 ("Tillett Hall", "Livingston"),
    AB2225 ("Academic Building", "College Avenue"),
    MU302 ("Murray Hall", "College Avenue");

    private String building;
    private String campus;

    Locations (String building, String campus)
    {
        this.building = building;
        this.campus = campus;
    }
}
