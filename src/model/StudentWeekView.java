package model;


public class StudentWeekView {
    private String name;

    private Integer mondayMark;

    private Integer tuesdayMark;

    private Integer wednesdayark;

    private Integer thursdayMark;

    private Integer fridayMark;

    public StudentWeekView() {

    }

    public StudentWeekView(String name, Integer mondayMark, Integer tuesdayMark, Integer wednesdayark, Integer thursdayMark, Integer fridayMark) {
        this.name = name;
        this.mondayMark = mondayMark;
        this.tuesdayMark = tuesdayMark;
        this.wednesdayark = wednesdayark;
        this.thursdayMark = thursdayMark;
        this.fridayMark = fridayMark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMondayMark() {
        return mondayMark;
    }

    public void setMondayMark(Integer mondayMark) {
        this.mondayMark = mondayMark;
    }

    public int getTuesdayMark() {
        return tuesdayMark;
    }

    public void setTuesdayMark(Integer tuesdayMark) {
        this.tuesdayMark = tuesdayMark;
    }

    public int getWednesdayark() {
        return wednesdayark;
    }

    public void setWednesdayark(Integer wednesdayark) {
        this.wednesdayark = wednesdayark;
    }

    public int getThursdayMark() {
        return thursdayMark;
    }

    public void setThursdayMark(Integer thursdayMark) {
        this.thursdayMark = thursdayMark;
    }

    public int getFridayMark() {
        return fridayMark;
    }

    public void setFridayMark(Integer fridayMark) {
        this.fridayMark = fridayMark;
    }
}
