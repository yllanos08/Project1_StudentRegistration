package registration;


public enum Time {
    PERIOD1("8:30", "9:50"),
    PERIOD2 ("10:20", "11:40"),
    PERIOD3("12:10", "13:30"),
    PERIOD4("14:00", "15:20"),
    PERIOD5("15:50", "17:10"),
    PERIOD6("17:40", "19:00");

    private final String start;
    private final String end;

    private Time (String start, String end)
    {
        this.start = start;
        this.end = end;
    }

    public String getStart() {return this.start;}
    public String getEnd() {return this.end;}
}
