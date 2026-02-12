package registration;


public enum Time {
    PERIOD1("8:30", "9:50"),
    PERIOD2 ("10:20", "11:40"),
    PERIOD3("12:10", "1:30"),
    PERIOD4("2:00", "3:20"),
    PERIOD5("3:50", "5:10"),
    PERIOD6("5:40", "7:00");

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
